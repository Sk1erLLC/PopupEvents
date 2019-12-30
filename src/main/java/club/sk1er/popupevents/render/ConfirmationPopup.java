package club.sk1er.popupevents.render;

import club.sk1er.popupevents.events.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class ConfirmationPopup {

    private final Queue<Confirmation> confirmations = new LinkedList<>();
    private Confirmation currentConfirmation;

    @SubscribeEvent
    public void friendRequest(HypixelFriendRequestEvent event) {
        displayConfirmation("Friend request from " + event.getFrom(), accept -> {
            FMLClientHandler.instance().getClient().thePlayer.sendChatMessage((accept ? "/friend accept " : "/friend deny ") + event.getFrom());
            currentConfirmation.framesLeft = 0;
        });
    }

    @SubscribeEvent
    public void partyInvite(HypixelPartyInviteEvent event) {
        displayConfirmation("Party request from " + event.getFrom(), accept -> {
            FMLClientHandler.instance().getClient().thePlayer.sendChatMessage("/party accept " + event.getFrom());
            currentConfirmation.framesLeft = 0;
        });
    }

    @SubscribeEvent
    public void tradeRequest(SkyblockTradeRequestEvent event) {
        displayConfirmation("Trade request from " + event.getUsername(), accept -> {
            FMLClientHandler.instance().getClient().thePlayer.sendChatMessage("/trade " + event.getUsername());
            currentConfirmation.framesLeft = 0;
        });
    }

    @SubscribeEvent
    public void duelRequest(HypixelDuelRequestEvent event) {
        displayConfirmation(event.getGame() + " Duel request from " + event.getUsername(), accept -> {
            FMLClientHandler.instance().getClient().thePlayer.sendChatMessage("/duel accept " + event.getUsername());
            currentConfirmation.framesLeft = 0;
        });
    }

    @SubscribeEvent
    public void guildInvite(HypixelGuildInviteEvent event) {
        displayConfirmation("Guild invite for " + event.getGuild(), accept -> {
            if (accept) {
                FMLClientHandler.instance().getClient().thePlayer.sendChatMessage("/guild accept " + event.getFrom());
            }

            currentConfirmation.framesLeft = 0;
        });
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

        if (Keyboard.getEventKey() == Keyboard.KEY_Y) {
            currentConfirmation.callback.accept(true);
        } else if (Keyboard.getEventKey() == Keyboard.KEY_N) {
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

        Confirmation(long framesLeft, long frames, String text, Consumer<Boolean> callback) {
            this.text = text;
            this.callback = callback;
            this.framesLeft = framesLeft;

            long fifth = frames / 5;
            upperThreshold = frames - fifth;
            lowerThreshold = fifth;
            percentComplete = 0.0f;
            FMLClientHandler.instance().getClient();
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

            Minecraft client = FMLClientHandler.instance().getClient();
            FontRenderer fr = client.fontRendererObj;
            ScaledResolution resolution = new ScaledResolution(client);

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
                String acceptDeny = EnumChatFormatting.GREEN + "[Y] Accept " + EnumChatFormatting.RED + "[N] Deny";
                fr.drawString(acceptDeny, resolution.getScaledWidth() / 2 - fr.getStringWidth(acceptDeny) / 2, 70, -1);
            }

            return false;
        }
    }
}
