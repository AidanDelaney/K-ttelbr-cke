package org.eulerdiagrams.euleR;

public class KöttelbrückeService {
    public KöttelbrückeService(JSONArea areaSpec) {
        EulerDrawer ed = new EulerDrawer(areaSpec);
        ed.layout();
    }
}
