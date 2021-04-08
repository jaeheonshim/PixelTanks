package com.jaeheonshim.pixeltanks.core;

import com.badlogic.gdx.math.MathUtils;

public class CollisionUtil {
    public static boolean pointCircle(float x1, float y1, float cx, float cy, float r) {
        return Math.sqrt(Math.pow(x1 - cx, 2) + Math.pow(y1 - cy, 2)) <= r;
    }

    public static boolean polyCircle(float[] vertices, float cx, float cy, float r) {
        for(int i = 0; i < vertices.length; i += 2) {
            float x1 = vertices[i];
            float y1 = vertices[i + 1];

            float x2;
            float y2;

            if(i + 2 >= vertices.length) {
                x2 = vertices[0];
                y2 = vertices[1];
            } else {
                x2 = vertices[i + 2];
                y2 = vertices[i + 3];
            }

            if(lineCircle(vertices[i], vertices[i + 1], x2, y2, cx, cy, r)) {
                return true;
            }
        }

        return polyPoint(vertices, cx, cy);
    }

    public static boolean polyPoint(float[] vertices, float px, float py) {
        boolean collides = false;

        for(int i = 0; i < vertices.length; i += 2) {
            float x1 = vertices[i];
            float y1 = vertices[i + 1];

            float x2;
            float y2;

            if(i + 2 >= vertices.length) {
                x2 = vertices[0];
                y2 = vertices[1];
            } else {
                x2 = vertices[i + 2];
                y2 = vertices[i + 3];
            }

            if ( ((y1 > py) != (y2 > py)) && (px < (x2-x1) * (py-y1) / (y2-y1) + x1) ) {
                collides = !collides;
            }
        }

        return collides;
    }

    public static boolean lineCircle(float x1, float y1, float x2, float y2, float cx, float cy, float r) {
        boolean inside1 = pointCircle(x1, y1, cx, cy, r);
        boolean inside2 = pointCircle(x2, y2, cx, cy, r);

        if(inside1 || inside2) return true; // check if either endpoint is within circle.

        float lineLen = pTheoremDist(x1, y1, x2, y2);
        float dot = (((cx - x1) * (x2 - x1)) + ((cy - y1) * (y2 - y1))) / (lineLen * lineLen); // WTF
        float closestX = x1 + (dot * (x2 - x2));
        float closestY = y1 + (dot * (y2 - y1));

        if(!linePoint(x1, y1, x2, y2, closestX, closestY)) {
            return false;
        }

        float distance = pTheoremDist(closestX, closestY, cx, cy);
        return distance <= r;
    }

    public static float pTheoremDist(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static boolean linePoint(float x1, float y1, float x2, float y2, float px, float py) {
        float dist1 = pTheoremDist(px, py, x1, y1);
        float dist2 = pTheoremDist(px, py, x2, y2); // find distance vectors

        float lineLen = pTheoremDist(x1, y1, x2, y2);
        float checkBuffer = 0.1f;

        if(dist1 + dist2 >= lineLen - checkBuffer && dist1 + dist2 <= lineLen + checkBuffer) {
            return true;
        }

        return false;
    }
}
