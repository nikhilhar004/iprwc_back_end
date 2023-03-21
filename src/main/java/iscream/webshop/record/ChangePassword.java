package iscream.webshop.record;

public record ChangePassword(String email, String newPassword, String oldPassword) {
}
