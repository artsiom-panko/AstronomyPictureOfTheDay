public class Application {

    public static void main(String[] args) {
        String imagePath = new ApiManager().getImage();
        WallpaperChanger.setScreenImage(imagePath);
    }
}
