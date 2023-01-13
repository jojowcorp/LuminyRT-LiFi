package fr.luminy_lifi.app;


import java.util.ArrayList;
import java.util.List;

public class PathUtils {

    public static List<dataManager.Point> findPathTo(dataManager.Point from, dataManager.Point to) {
        List<dataManager.Point> ret = new ArrayList<dataManager.Point>();
        ret.add(from);
        for(int i:from.connectedPoint) {
            dataManager.Point p = MainActivity.instance.data.getPointById(i);
            if(p != null) {
                if (p.ID == to.ID) {
                    ret.add(to);
                    break;
                }
                List<dataManager.Point> temp = travelPath(from.ID, p, to);
                if(!temp.isEmpty()) {
                    ret.add(p);
                    ret.addAll(temp);
                    break;
                }
            } else {
                //TODO ALERT POINT TO SUPP
            }
        }
        return ret;
    }


    private static List<dataManager.Point> travelPath(int base, dataManager.Point from, dataManager.Point to) {
        List<dataManager.Point> adt = new ArrayList<dataManager.Point>();
        for(int i:from.connectedPoint) {
            if(i != base) {
                dataManager.Point p = MainActivity.instance.data.getPointById(i);
                if(p != null) {
                    if (i == to.ID){
                        adt.add(to);
                        break;
                    } else {
                        List<dataManager.Point> temp = travelPath(from.ID, p, to);
                        if(!temp.isEmpty()) {
                            adt.add(p);
                            adt.addAll(temp);
                            break;
                        }
                    }
                } else {
                    //TODO ALERT POINT TO SUPP
                }
            }
        }
        return adt;

    }
}
