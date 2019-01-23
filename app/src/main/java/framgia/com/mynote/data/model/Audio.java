package framgia.com.mynote.data.model;

public class Audio {
    private String mName;
    private String mPath;

    public Audio(String name, String path) {
        this.mName = name;
        this.mPath = path;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        this.mPath = path;
    }
}
