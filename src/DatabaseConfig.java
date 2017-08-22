
public interface DatabaseConfig {
    //ToDo: This needs to be modified according to your local machine database installation (especially username and password)
    public static final String DATABASE_URL = "jdbc:mysql://localhost/janbask_training";
    public static final String DATABASE_USERNAME = "applicationUser";
    public static final String DATABASE_PASSWORD = "app@Passw0rd";
    
    public static final String STUDENT_REGISTER_PROC_CALL = "call usp_registerstudent(?,?,?,?,?,?);";
}
