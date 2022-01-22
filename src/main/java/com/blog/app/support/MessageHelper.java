package com.blog.app.support;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.blog.app.support.Message.MESSAGE_ATTRIBUTE;

@RequiredArgsConstructor
public final class MessageHelper {

    public static void addAttribute(RedirectAttributes redirectAttributes, String message, Message.Type type, Object... args){
        redirectAttributes.addFlashAttribute(MESSAGE_ATTRIBUTE, new Message(message, type, args));
    }
    public static void addSuccessAttribute(RedirectAttributes redirectAttributes, String message, Object... args){
        addAttribute(redirectAttributes, message, Message.Type.SUCCESS, args);
    }
    public static void addErrorAttribute(RedirectAttributes redirectAttributes, String message, Object... args){
        addAttribute(redirectAttributes, message, Message.Type.DANGER, args);
    }
    public static void addWarningAttribute(RedirectAttributes redirectAttributes, String message, Object... args){
        addAttribute(redirectAttributes, message, Message.Type.WARNING, args);
    }
}
