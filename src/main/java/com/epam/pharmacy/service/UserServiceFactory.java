package com.epam.pharmacy.service;

import com.epam.pharmacy.util.constant.Role;

public interface UserServiceFactory {
    UserService getUserService(Role role);
}
