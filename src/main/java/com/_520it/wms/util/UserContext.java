package com._520it.wms.util;


import com._520it.wms.domain.Employee;
import com.opensymphony.xwork2.ActionContext;

import java.util.Set;

public class UserContext {

    public static final String USER_IN_SESSION="user_in_session";
    public static final String PERMISSION_IN_SESSION="permission_in_session";

    public static  void setCurrentUser(Employee currentUser){
        ActionContext.getContext().getSession().put(UserContext.USER_IN_SESSION,currentUser);
    }

    public static void setCurrentUserPermissions(Set<String> permissions){
        ActionContext.getContext().getSession().put(UserContext.PERMISSION_IN_SESSION,permissions);
    }

    public static Employee getCurrentUser() {
        return (Employee) ActionContext.getContext().getSession().get(UserContext.USER_IN_SESSION);
    }

    public static Set<String> getCurrentUserPermissions(){
        return (Set<String>) ActionContext.getContext().getSession().get(UserContext.PERMISSION_IN_SESSION);
    }
}
