package iscream.webshop.record;

import iscream.webshop.model.UserRole;

public record RegisterRequest(String name, String email, UserRole role, String password) {
}
