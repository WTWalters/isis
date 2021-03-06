/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.isis.core.runtime.services.authsess;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.core.commons.authentication.AuthenticationSession;
import org.apache.isis.core.commons.authentication.AuthenticationSessionProvider;
import org.apache.isis.core.metamodel.services.user.UserServiceDefault;
import org.apache.isis.core.runtime.authentication.standard.SimpleSession;
import org.apache.isis.core.runtime.system.session.IsisSessionFactory;

@DomainService(nature = NatureOfService.DOMAIN)
public class AuthenticationSessionProviderDefault implements AuthenticationSessionProvider {

     /**
      * This class and {@link UserServiceDefault} both call each other, so the code below is carefully
      * ordered to ensure no infinite loop.
      *
      * In particular, we check if there are overrides, and if so return a {@link SimpleSession} to represent those
      * overrides.
      */
    @Programmatic
    @Override
    public AuthenticationSession getAuthenticationSession() {

        // if user/role has been overridden by SudoService, then honour that value.
        final UserServiceDefault.UserAndRoleOverrides userAndRoleOverrides =
                userServiceDefault.currentOverridesIfAny();

        if(userAndRoleOverrides != null) {
            final String user = userAndRoleOverrides.getUser();
            final List<String> roles = userAndRoleOverrides.getRoles();
            return new SimpleSession(user, roles);
        }

        // otherwise...
        return isisSessionFactory.getCurrentSession().getAuthenticationSession();
    }

    @javax.inject.Inject
    UserServiceDefault  userServiceDefault;

    @javax.inject.Inject
    IsisSessionFactory isisSessionFactory;

}
