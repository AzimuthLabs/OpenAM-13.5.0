/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2010 Sun Microsystems Inc. All Rights Reserved
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * https://opensso.dev.java.net/public/CDDLv1.0.html or
 * opensso/legal/CDDLv1.0.txt
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at opensso/legal/CDDLv1.0.txt.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * $Id: SubRealmObserver.java,v 1.3 2010/01/20 17:01:36 veiming Exp $
 *
 * Portions Copyrighted 2014-2016 ForgeRock AS.
 */

package com.sun.identity.entitlement.opensso;

import static org.forgerock.openam.entitlement.PolicyConstants.SUPER_ADMIN_SUBJECT;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.getApplicationService;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.getEntitlementConfiguration;

import java.security.AccessController;

import javax.security.auth.Subject;

import com.iplanet.sso.SSOException;
import com.iplanet.sso.SSOToken;
import com.sun.identity.entitlement.EntitlementConfiguration;
import com.sun.identity.entitlement.EntitlementException;
import com.sun.identity.entitlement.PrivilegeManager;
import com.sun.identity.idm.IdConstants;
import com.sun.identity.security.AdminTokenAction;
import com.sun.identity.setup.SetupListener;
import com.sun.identity.sm.DNMapper;
import com.sun.identity.sm.SMSException;
import com.sun.identity.sm.ServiceConfigManager;
import com.sun.identity.sm.ServiceListener;

/**
 * This observer will remove all referral and application privileges
 * that have reference to a deleted sub realm.
 * 
 */
public class SubRealmObserver implements ServiceListener, SetupListener {
    private static Subject adminSubject = SubjectUtils.createSuperAdminSubject();

    @Override
    public void setupComplete() {
        registerListener();
    }

    private static void registerListener() {
        SSOToken adminToken =
            (SSOToken) AccessController.doPrivileged(
            AdminTokenAction.getInstance());
        try {
            ServiceConfigManager scm = new ServiceConfigManager(
                IdConstants.REPO_SERVICE, adminToken);
            scm.addListener(new SubRealmObserver());
        } catch (SMSException e) {
            PrivilegeManager.debug.error(
                "SubRealmObserver.registerListener", e);
        } catch (SSOException e) {
            PrivilegeManager.debug.error(
                "SubRealmObserver.registerListener", e);
        }
    }
    
    public void schemaChanged(String serviceName, String version) {
        // do nothing
    }

    public void globalConfigChanged(String serviceName, String version,
        String groupName, String serviceComponent, int type) {
        // do nothing
    }

    public void organizationConfigChanged(
        String serviceName,
        String version,
        String orgName,
        String groupName,
        String serviceComponent,
        int type
    ) {
        // Only clear cache and remove referrals if the realm is being removed
        if (type == ServiceListener.REMOVED &&
                (serviceComponent == null || serviceComponent.trim().isEmpty() || serviceComponent.equals("/"))) {
            getApplicationService(SUPER_ADMIN_SUBJECT, DNMapper.orgNameToRealmName(orgName)).clearCache();
            try {
                OpenSSOApplicationPrivilegeManager.removeAllPrivileges(orgName);
            } catch (EntitlementException ex) {
                PrivilegeManager.debug.error(
                    "SubRealmObserver.organizationConfigChanged: " +
                    "Unable to remove application  privileges", ex);
            }

        } else if (type == ServiceListener.MODIFIED) {
            getApplicationService(SUPER_ADMIN_SUBJECT, DNMapper.orgNameToRealmName(orgName)).clearCache();
        }
    }

}
