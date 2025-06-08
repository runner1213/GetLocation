package org.cats.recent;

import static org.cats.core.UI.domainList;

public class SetRecent {
    public static void setRecent(String domain) {
        String subdomain = rmSubdomain(domain);
        if (subdomain != null && !subdomain.trim().isEmpty()) {
            for (int i = 0; i < domainList.getItemCount(); i++) {
                if (domainList.getItemAt(i).equals(subdomain)) {
                    return;
                }
            }
            domainList.addItem(rmSubdomain(domain));
        }
    }
    private static String rmSubdomain(String domain) {
        String[] parts = domain.split("\\.");
        if (parts.length >= 2) {
            return parts[parts.length - 2] + "." + parts[parts.length - 1];
        }
        else {
            return null;
        }
    }
}
