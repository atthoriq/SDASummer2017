import java.io.*;
import java.util.*;

import static java.lang.System.out;

/**
 * Created by esak on 12/9/2017.
 */

class FolderFolderan {

    Folder root;
    Map<String, Folder> folderIndexMap;
    Map<String, Set<File>> fileIndexMap;

    public FolderFolderan() {
        this.root = new Folder("root", null);
        this.folderIndexMap = new HashMap<String, Folder>();
        folderIndexMap.put("root", root);
        this.fileIndexMap = new HashMap<String, Set<File>>();
    }

    boolean add(String folder, String parent) {
        return folderIndexMap.get(parent).add(new Folder(folder, folderIndexMap.get(parent)));
    }

    boolean insert(String name, String type, int size, String folder) {
        return folderIndexMap.get(folder).insert(new File(name, type, size, folderIndexMap.get(folder)));
    }

    void putFile(File file) {
        if (!fileIndexMap.containsKey(file.name))
            fileIndexMap.put(file.name, new HashSet<File>());
        fileIndexMap.get(file.name).add(file);
    }

    void printPath(String content) {
        Map<String, Set<String>> path = new TreeMap<String, Set<String>>();
        Map<String, List<String>> files = new TreeMap<String, List<String>>();

        path.put("root", new TreeSet<String>());

        boolean found = false;

        if (fileIndexMap.containsKey(content) && fileIndexMap.get(content).size() != 0) {
            found = true;
            for (File file : fileIndexMap.get(content)) {
                Folder now = file.parent;

                if (!path.containsKey(now.name))
                    path.put(now.name, new TreeSet<String>());

                if (!files.containsKey(now.name))
                    files.put(now.name, new ArrayList<String>());

                files.get(now.name).add(file.name + "." + file.type);

                Folder prev = now;
                now = now.parent;

                while (now != null) {
                    if (!path.containsKey(now.name))
                        path.put(now.name, new TreeSet<String>());

                    path.get(now.name).add(prev.name);
                    prev = now;
                    now = now.parent;
                }
            }
        }

        if (folderIndexMap.containsKey(content)) {
            found = true;
            Folder now = folderIndexMap.get(content);
            Folder prev = now;

            now = now.parent;

            while (now != null) {
                if (!path.containsKey(now.name))
                    path.put(now.name, new TreeSet<String>());

                path.get(now.name).add(prev.name);
                prev = now;
                now = now.parent;
            }
        }

        if (found)
            printPathRecursively(path, "root", "> ", files);
    }

    void printPathRecursively(Map<String, Set<String>> path, String now, String depth, Map<String, List<String>> files) {
        out.println(depth + now);
        if (path.containsKey(now)) {
            for (String next : path.get(now)) {
                printPathRecursively(path, next, "  " + depth, files);
            }
        }

        if (files.containsKey(now)) {
            for (String file : files.get(now))
                out.println("  " + depth + file);
        }
    }

    Map<String, Integer> countSize(String folder, Map<String, Integer> sizes) {
        Folder targetFolder = folderIndexMap.get(folder);
        int size = 1;
        if (targetFolder.containedFolders.size() == 0) {
            for (File files : targetFolder.containedFiles)
                size += files.size;
            sizes.put(targetFolder.name, size);
        } else {
            for (Folder folders : targetFolder.containedFolders) {
                countSize(folders.name, sizes);
                size += sizes.get(folders.name);
            }

            sizes.put(targetFolder.name, size);
        }
        return sizes;
    }

    void print(String folder) {
        Map<String, Integer> sizes = countSize(folder, new HashMap<String, Integer>());
        print(folderIndexMap.get(folder), 0, sizes);
    }

    void print(Folder folder, int depth, Map<String, Integer> sizes) {
        int indent = depth * 2;
        while (indent > 0) {
            out.print(" ");
            indent--;
        }
        out.println("> " + folder.name + " " + sizes.get(folder.name));
        if (folder.containedFolders.size() == 0) {
            for (File file : folder.containedFiles) {
                int fileIndent = depth * 2;
                while (fileIndent > 0) {
                    out.print(" ");
                    fileIndent--;
                }
                out.print("  ");
                out.println("> " + file.name + "." + file.type + " " + file.size);
            }
        } else {
            for (Folder folders : folder.containedFolders)
                print(folders, depth + 1, sizes);
        }
    }

    void remove(String content) {
        if (folderIndexMap.containsKey(content)) {
            Folder targetFolder = folderIndexMap.get(content);
            targetFolder.remove();
            targetFolder.parent.containedFolders.remove(targetFolder);
            folderIndexMap.remove(targetFolder.name);
            out.println("Folder " + targetFolder.name + " removed");
        }

        if (fileIndexMap.containsKey(content)) {
            int removedFiles = 0;
            for (File files : fileIndexMap.get(content)) {
                files.parent.containedFiles.remove(files);
                removedFiles++;
            }

            fileIndexMap.remove(content);
            out.println(removedFiles + " File " + content + " removed");
        }
    }

    class File {
        String name;
        String type;
        Folder parent;
        int size;

        public File(String name, String type, int size, Folder parent) {
            this.name = name;
            this.type = type;
            this.size = size;
            this.parent = parent;
        }

    }

    class Folder {
        String name;
        Folder parent;
        TreeSet<Folder> containedFolders;
        TreeSet<File> containedFiles;

        public Folder(String name, Folder parent) {
            this.name = name;
            this.parent = parent;
            containedFiles = new TreeSet<File>(new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    if (o1.name.equals(o2.name)) {
                        if (o1.type.equals(o2.type)) {
                            return Integer.compare(o1.size, o2.size);
                        } else return o1.type.compareTo(o2.type);
                    } else return o1.name.compareTo(o2.name);
                }
            });
            containedFolders = new TreeSet<Folder>(new Comparator<Folder>() {
                @Override
                public int compare(Folder o1, Folder o2) {
                    return o1.name.compareTo(o2.name);
                }
            });
        }

        public Folder() {
        }

        void remove() {
            if (containedFolders.size() == 0) {
                for (File files : containedFiles) {
                    fileIndexMap.get(files.name).remove(files);
                }
                containedFiles.clear();
            } else {
                for (Folder folders : containedFolders) {
                    folders.remove();
                    folderIndexMap.remove(folders.name);
                }
                containedFolders.clear();
            }
        }

        boolean add(Folder addFolder) {
            if (containedFolders.size() == 0) {
                for (File files : containedFiles) {
                    files.parent = addFolder;
                    addFolder.containedFiles.add(files);
                }

                containedFiles.clear();
                containedFolders.add(addFolder);
            } else {
                containedFolders.add(addFolder);
            }

            folderIndexMap.put(addFolder.name, addFolder);
            return true;
        }

        boolean insert(File addFile) {
            boolean result = this.insertWithoutFailover(addFile, null);
            Folder now = this;

            while (!result) {
                if (now.parent == null)
                    break;

                result = now.parent.insertWithoutFailover(addFile, now);
                now = now.parent;
            }

            return result;
        }

        boolean insertWithoutFailover(File addFile, Folder prevFolder) {
            if (containedFolders.size() == 0) {
                if (containedFiles.isEmpty()) {
                    addFile.parent = this;
                    containedFiles.add(addFile);
                    putFile(addFile);
                    out.println(addFile.name + "." + addFile.type + " added to " + addFile.parent.name);
                    return true;
                }

                if (addFile.type.equals(containedFiles.first().type)) {
                    addFile.parent = this;
                    containedFiles.add(addFile);
                    putFile(addFile);
                    out.println(addFile.name + "." + addFile.type + " added to " + addFile.parent.name);
                    return true;
                } else {
                    return false;
                }
            } else {
                if (prevFolder == null) {
                    Iterator<Folder> folderIterator = containedFolders.iterator();
                    while (folderIterator.hasNext()) {
                        if (folderIterator.next().insertWithoutFailover(addFile, null))
                            return true;
                    }
                    return false;
                } else {
                    Iterator<Folder> tailFolderIterator = containedFolders.tailSet(prevFolder, false).iterator();
                    while (tailFolderIterator.hasNext()) {
                        if (tailFolderIterator.next().insertWithoutFailover(addFile, null))
                            return true;
                    }

                    Iterator<Folder> headFolderIterator = containedFolders.headSet(prevFolder, false).iterator();
                    while (headFolderIterator.hasNext()) {
                        if (headFolderIterator.next().insertWithoutFailover(addFile, null))
                            return true;
                    }

                    return false;
                }
            }
        }
    }
}

public class RafiePunya {
    public static void main(String[] args) throws java.lang.Exception {
        init();

        FolderFolderan root = new FolderFolderan();

        while (true) {
            String[] input = {};

            // Reads until end of file
            try {
                input = in.nextLine().split(" ");
            } catch (NullPointerException e) {
                break;
            }

            switch (input[0]) {
                case ("add"):
                    root.add(input[1], input[2]);
                    break;
                case ("insert"):
                    String[] file = input[1].split("\\.");
                    root.insert(file[0], file[1], Integer.parseInt(input[2]), input[3]);
                    break;
                case ("print"):
                    root.print(input[1]);
                    break;
                case ("remove"):
                    root.remove(input[1]);
                    break;
                case ("search"):
                    root.printPath(input[1]);
                    break;
            }
        }
        out.flush();
        out.close();
    }

    /* PrintWriter and BufferedReader Template from Codeforces */

    public static MyScanner in;
    public static PrintWriter out;

    public static void init() {
        in = new MyScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
    }

    public static class MyScanner {
        BufferedReader br;
        StringTokenizer st;

        MyScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
    /* End of Template */
}



