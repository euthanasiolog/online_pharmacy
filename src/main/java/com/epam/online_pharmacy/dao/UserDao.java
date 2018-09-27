package com.epam.online_pharmacy.dao;

import com.epam.online_pharmacy.dao.impl.ClientDaoImpl;
import com.epam.online_pharmacy.dao.impl.PharmacistDaoImpl;
import com.epam.online_pharmacy.entity.user.User;
import com.epam.online_pharmacy.util.constant.Role;


public interface UserDao extends AbstractDao {
    static User signIn(String login, String password, Role role){
                    switch (role) {
                        case CLIENT:
                            return new ClientDaoImpl().signIn(login, password);
                        case DOCTOR:
                            return new ClientDaoImpl().signIn(login, password);
                        case PHARMACIST:
                            return new PharmacistDaoImpl().signIn(login, password);
                        default: return null;
                    }
    }
    User signIn(String login, String password);
}
