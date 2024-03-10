package com.muhammadfarazrashid.i2106595;

public class AllMessagesChat {

    private Mentor mentor;
    private String id;
    private String userName;
    private int unreadMessages;
    private String otherUserImage;

    public void setMentor(Mentor mentor){
        this.mentor = mentor;
    }
    public Mentor getMentor(){
        return mentor;
    }


    public AllMessagesChat(String id, int unreadMessages) {
        this.id = id;
        this.unreadMessages = unreadMessages;

        // Fetch mentor details asynchronously
    }

    public void setOtherUserImage(String otherUserImage) {
        this.otherUserImage = otherUserImage;
    }

    public String getId() {
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public int getUnreadMessages() {
        return unreadMessages;
    }

    public String getOtherUserImage() {
        return otherUserImage;
    }
}
