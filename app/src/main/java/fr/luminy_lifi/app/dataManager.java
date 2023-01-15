package fr.luminy_lifi.app;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dataManager {

    private List<Point> points;

    public dataManager() {
        this.points = new ArrayList<Point>();
    }

    public void init() {
        new downloadPointTask().execute();
    }


    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return this.points;
    }

    public Point getPointById(int id) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return this.points.stream().filter(x -> x.ID == id).findFirst().orElse(null);
        } else {
            for(Point p:this.points) {
                if(p.ID == id) {
                    return p;
                }
            }
            return null;
        }
    }


    public static class Point {
        public final int X, Y;
        public final int ID;

        public int[] connectedPoint = null;

        public Point(int ID, int X, int Y) {
            this.ID = ID;
            this.X = X;
            this.Y = Y;
        }

        @Override
        public String toString() {
            return "(Point "+X+" "+Y+" id:"+ID+") "+ Arrays.toString(connectedPoint);
        }

        @Override
        public int hashCode() {
            return (int) (this.ID + this.X + this.Y);
        }
        @Override
        public boolean equals(Object o) {
            if (o == null)
                return false;
            if (getClass() != o.getClass())
                return false;
            Point vec = (Point) o;

            return (vec.ID == this.ID && vec.X == this.X &&  vec.Y == this.Y);
        }

    }

    public class LocationPoint extends Point {
        public final String Name;


        public LocationPoint(int ID, int X, int Y, String Name) {
            super(ID,X,Y);
            this.Name = Name;
        }

        @Override
        public String toString() {
            return "(Point "+Name+" "+X+" "+Y+" id:"+ID+") "+Arrays.toString(connectedPoint);
        }

    }

    public class PathPoint extends Point {

        public PathPoint(int ID, int X, int Y) {
            super(ID,X,Y);
        }
    }

    private class downloadPointTask extends AsyncTask<Void, Void, List<Point>> {
        @Override
        protected List<Point> doInBackground(Void... params) {
            List<Point> points = new ArrayList<Point>();
            try {
                StringBuilder sb = new StringBuilder();
                URL url = new URL("https://hugo-mathieu-1-etu.pedaweb.univ-amu.fr/extranet/requestPoint.php");

                BufferedReader in;
                in = new BufferedReader(
                        new InputStreamReader(
                                url.openStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    sb.append(inputLine);

                in.close();

                JSONArray array = new JSONArray(sb.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int id = object.getInt("id");
                    String name = null;
                    if (!object.isNull("Name")) {
                        name = object.getString("Name");
                    }
                    String connexion = null;
                    if(!object.isNull("connexion")) {
                        connexion = object.getString("connexion");
                    }
                    int xPos = object.getInt("xPos");
                    int yPos = object.getInt("yPos");

                    Point p = null;
                    if(name != null) {
                        p = new LocationPoint(id, xPos, yPos, name);
                    } else {
                        p = new PathPoint(id, xPos, yPos);
                    }
                    int[] arrayId = null;
                    if(connexion != null) {
                        String[] arrayIdStrSplit = connexion.split(",");
                        int size = arrayIdStrSplit.length;
                        arrayId = new int[size];
                        for(int i2 = 0; i2 < size; i2++) {
                            arrayId[i2] = Integer.parseInt(arrayIdStrSplit[i2]);
                        }
                    }
                    p.connectedPoint = arrayId;
                    points.add(p);
                }
            } catch(Exception e){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.instance);
                builder.setTitle("Erreur")
                        .setMessage(e.getMessage())
                        .setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }


            return points;
        }

        @Override
        protected void onPostExecute(List<Point> result) {
            MainActivity.instance.data.setPoints(result);
            MainActivity.instance.defineNewUserAt(1);
            SharedPreferences sharedPreferences = MainActivity.instance.getPreferences(MainActivity.MODE_PRIVATE);
            if(sharedPreferences.contains("url_calendar")) {
                String url = sharedPreferences.getString("url_calendar", "");
                new MainActivity3.downloadCalendar().execute(url);
            }
        }
    }

}
