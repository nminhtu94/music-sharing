public class Main {
    public static void main(String[] args) {
        System.out.println("[*] Music Sharing");
        MusicSharingForm app = new MusicSharingForm();
        NavigationController.getInstance().pushFrame(app);
    }
}
