package club.sk1er.popupevents.render;

import club.sk1er.popupevents.PopupEvents;
import club.sk1er.popupevents.config.PopupConfig;
import club.sk1er.popupevents.events.HypixelDuelRequestEvent;
import club.sk1er.popupevents.events.HypixelFriendRequestEvent;
import club.sk1er.popupevents.events.HypixelGuildInviteEvent;
import club.sk1er.popupevents.events.HypixelPartyInviteEvent;
import club.sk1er.popupevents.events.SkyblockTradeRequestEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class ConfirmationPopup {

    private final Queue<Confirmation> confirmations = new LinkedList<>();
    private final Minecraft mc = Minecraft.getMinecraft();
    private Confirmation currentConfirmation;

    @SubscribeEvent
    public void friendRequest(HypixelFriendRequestEvent event) {
        if (PopupConfig.friendRequestsRequest) {
            playPingNoise();
            displayConfirmation("Friend request from " + event.getFrom(), accept -> {
                mc.thePlayer.sendChatMessage((accept ? "/friend accept " : "/friend deny ") + event.getFrom());
                currentConfirmation.framesLeft = 0;
            });
        }
    }

    @SubscribeEvent
    public void partyInvite(HypixelPartyInviteEvent event) {
        if (PopupConfig.partyInviteRequest) {
            playPingNoise();
            displayConfirmation("Party request from " + event.getFrom(), accept -> {
                if (accept) {
                    mc.thePlayer.sendChatMessage("/party accept " + event.getFrom());
                }

                currentConfirmation.framesLeft = 0;
            });
        }
    }

    @SubscribeEvent
    public void tradeRequest(SkyblockTradeRequestEvent event) {
        if (PopupConfig.skyblockTradeRequest) {
            playPingNoise();
            displayConfirmation("Trade request from " + event.getUsername(), accept -> {
                if (accept) {
                    mc.thePlayer.sendChatMessage("/trade " + event.getUsername());
                }

                currentConfirmation.framesLeft = 0;
            });
        }
    }

    @SubscribeEvent
    public void duelRequest(HypixelDuelRequestEvent event) {
        if (PopupConfig.duelRequests) {
            playPingNoise();
            displayConfirmation(event.getGame() + " Duel request from " + event.getUsername(), accept -> {
                if (accept) {
                    mc.thePlayer.sendChatMessage("/duel accept " + event.getUsername());
                }

                currentConfirmation.framesLeft = 0;
            });
        }
    }

    @SubscribeEvent
    public void guildInvite(HypixelGuildInviteEvent event) {
        if (PopupConfig.guildInviteRequest) {
            playPingNoise();
            displayConfirmation("Guild invite for " + event.getGuild(), accept -> {
                if (accept) {
                    mc.thePlayer.sendChatMessage("/guild accept " + event.getFrom());
                }

                currentConfirmation.framesLeft = 0;
            });
        }
    }

    private void playPingNoise() {
        if (!PopupConfig.pingRequest) return;

        SoundHandler soundHandler = mc.getSoundHandler();

        if (soundHandler != null) {
            soundHandler.playSound(new PositionedSoundRecord(new ResourceLocation("note.pling"),
                1,
                2,
                (float) mc.thePlayer.posX,
                (float) mc.thePlayer.posY,
                (float) mc.thePlayer.posZ));
        }
    }

    @SubscribeEvent
    public void renderTick(TickEvent.RenderTickEvent event) {
        if (currentConfirmation == null) {
            currentConfirmation = confirmations.poll();
            return;
        }

        if (currentConfirmation.render()) currentConfirmation = confirmations.poll();
    }

    @SubscribeEvent
    public void keyPress(InputEvent.KeyInputEvent event) {
        if (currentConfirmation == null) return;

        if (Keyboard.getEventKey() == PopupEvents.instance.getKeybindAccept().getKeyCode()) {
            currentConfirmation.callback.accept(true);
        } else if (Keyboard.getEventKey() == PopupEvents.instance.getKeybindDeny().getKeyCode()) {
            currentConfirmation.callback.accept(false);
        }
    }

    private void displayConfirmation(String text, Consumer<Boolean> callback) {
        Confirmation confirmation = new Confirmation(5 * 60, 5 * 60, text, callback);
        confirmations.add(confirmation);
    }

    private static float clamp(float number) {
        return number < (float) 0.0 ? (float) 0.0 : Math.min(number, (float) 1.0);
    }

    private static float easeOut(float current, float goal) {
        if (Math.floor(Math.abs(goal - current) / (float) 0.01) > 0) {
            return current + (goal - current) / (float) 15.0;
        } else {
            return goal;
        }
    }

    private static class Confirmation {
        private final String text;
        private final Consumer<Boolean> callback;
        private final long upperThreshold;
        private final long lowerThreshold;
        private long framesLeft;
        private float percentComplete;
        private long systemTime;
        private final Minecraft mc = Minecraft.getMinecraft();
        private final FontRenderer fr = mc.fontRendererObj;

        Confirmation(long framesLeft, long frames, String text, Consumer<Boolean> callback) {
            this.text = text;
            this.callback = callback;
            this.framesLeft = framesLeft;

            long fifth = frames / 5;
            upperThreshold = frames - fifth;
            lowerThreshold = fifth;
            percentComplete = 0.0f;
            systemTime = Minecraft.getSystemTime();
        }

        boolean render() {
            if (framesLeft <= 0) {
                return true;
            }

            String acceptFrom = "";
            if (text.equalsIgnoreCase("Party request from " + acceptFrom)) {
                callback.accept(true);
                return true;
            }

            while (systemTime < Minecraft.getSystemTime() + (1000 / 60)) {
                framesLeft--;
                systemTime += (1000 / 60);
            }

            percentComplete = clamp(
                easeOut(
                    percentComplete,
                    framesLeft < lowerThreshold ? 0.0f :
                        framesLeft > upperThreshold ? 1.0f : framesLeft
                )
            );

            ScaledResolution resolution = new ScaledResolution(mc);

            int middle = resolution.getScaledWidth() / 2;
            int halfWidth = 105;
            int currentWidth = (int) (halfWidth * percentComplete);

            Gui.drawRect(
                middle - currentWidth,
                50,
                middle + currentWidth,
                95,
                new Color(27, 27, 27).getRGB()
            );

            if (percentComplete == 1.0F) {
                long length = upperThreshold - lowerThreshold;
                long current = framesLeft - lowerThreshold;
                float progress = 1.0F - clamp((float) current / (float) length);

                Gui.drawRect(
                    middle - currentWidth,
                    93,
                    (int) (middle - currentWidth + (210 * progress)),
                    95,
                    new Color(128, 226, 126).getRGB()
                );

                fr.drawString(text, resolution.getScaledWidth() / 2 - fr.getStringWidth(text) / 2, 58, -1);
                String acceptDeny =
                    EnumChatFormatting.GREEN + "[" + Keyboard.getKeyName(PopupEvents.instance.getKeybindAccept().getKeyCode()) + "] Accept " +
                        EnumChatFormatting.RED + "[" + Keyboard.getKeyName(PopupEvents.instance.getKeybindDeny().getKeyCode()) + "] Deny";
                fr.drawString(acceptDeny, resolution.getScaledWidth() / 2 - fr.getStringWidth(acceptDeny) / 2, 70, -1);
            }

            return false;
        }
    }
}
