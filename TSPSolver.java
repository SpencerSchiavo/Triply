package project.triply.triply_app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TSPSolver {

    private DistanceMatrix distMat;
    private boolean isChecked;

    public TSPSolver() {
        distMat = new DistanceMatrix();
    }

    public TSPSolver(DistanceMatrix distMat, boolean isChecked) {
        this.distMat = distMat;
        this.isChecked = isChecked;
    }

    public String[] findShortestPath(int[] indices, DistanceMatrix distMat) { // delete distance matrix
        ArrayList<int[]> perms = new ArrayList<>();
        HashMap<String, Integer> distanceMap = new HashMap<>();
        perms = generatePath(indices, indices.length - 1);
        distanceMap = generateDistances(perms, distMat);
        String[] pathDistPair = new String[2];
        String basePathKey = (String) distanceMap.keySet().toArray()[0];
        int dist = distanceMap.get(basePathKey);
        for (String pathKey : distanceMap.keySet()) {
            int tempDist = distanceMap.get(pathKey);
            if (tempDist < dist) {
                dist = tempDist;
                basePathKey = pathKey;
            }
        }
        pathDistPair[0] = basePathKey;
        pathDistPair[1] = String.valueOf(dist);
        return pathDistPair;
    }

    private HashMap<String, Integer> generateDistances(ArrayList<int[]> perms, DistanceMatrix distMat) { // delete distance matrix
        HashMap<String, Integer> distanceMap = new HashMap<>();
        int len = perms.get(0).length;
        for (int i = 0; i < perms.size(); i++) {
            int tempDistance = 0;
            for (int j = 0; j < len - 1; j++) {
                int tempOriginIndex = perms.get(i)[j];
                int tempDestIndex = perms.get(i)[j + 1];
                tempDistance += distMat.getDistMat()[tempOriginIndex][tempDestIndex];
            }
            distanceMap.put(numToString(perms.get(i)), tempDistance);
        }
        return distanceMap;
    }

    /**
     * Method converts array representing path to string
     * Credit to Hasasn on StackOverflow for the regex implementation.
     * @param indices the integer array representing the path
     * @return A String array containing the paths as strings
     */
    private String numToString(int[] indices) {
        String tempString = new String();
        tempString = Arrays.toString(indices)
                .replaceAll("\\[|\\]|,|\\s", "");;
        return tempString;
    }

    private ArrayList<int[]> generatePath(int[] indices, int n) {
        ArrayList<int[]> perms = new ArrayList<>();
        int[] index = new int[n];
        int[] newIndices = truncateOrigin(indices);
        int[] firstEntry = new int[newIndices.length];
        for (int i = 0; i < n; i++) {
            index[i] = 0;
            firstEntry[i] = newIndices[i];
        }
        perms.add(firstEntry);
        int i = 0;
        while (i < n) {
            if (index[i] < i) {
                perms.add(swap(newIndices, i % 2 == 0 ?  0: index[i], i));
                index[i]++;
                i = 0;
            } else {
                index[i] = 0;
                i++;
            }
        }
        return appendOrigin(perms, isChecked);
    }

    private int[] swap(int[] indices, int originalIndex, int otherIndex) {
        int[] newIndices = new int[indices.length];
        int temp = indices[originalIndex];
        indices[originalIndex] = indices[otherIndex];
        indices[otherIndex] = temp;
        for (int i = 0; i < newIndices.length; i++) {
            newIndices[i] = indices[i];
        }
        return newIndices;
    }

    private int[] truncateOrigin(int[] indices) { // 0 will always be the origin index
        int[] newIndices = new int[indices.length - 1];
        for (int i = 1; i < indices.length; i++) {
            newIndices[i-1] = indices[i];
        }
        return newIndices;
    }

    private ArrayList<int[]> appendOrigin(ArrayList<int[]> perms, boolean isChecked) {
        ArrayList<int[]> newPerms = new ArrayList<>(perms.size());
        int newLength;
        if (isChecked) {
            newLength = perms.get(0).length + 2; // append origin at front and back (if isChecked is true)
        } else {
            newLength = perms.get(0).length + 1;
        }
        for (int i = 0; i < perms.size(); i++) {
            int[] newIndices = new int[newLength];
            for (int j = 0; j < newLength; j++) {
                if (j == 0) {
                    newIndices[j] = 0; // 0 is the origin
                } else if (j == newLength - 1 && isChecked) {
                    newIndices[j] = 0;
                } else {
                    newIndices[j] = perms.get(i)[j - 1];
                }
            }
            newPerms.add(newIndices);
        }
        return newPerms;
    }
}
