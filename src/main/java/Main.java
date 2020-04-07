import com.carx.config.SpringContextConfig;
import com.carx.controller.ActivityController;
import com.carx.controller.AnalyticController;
import com.carx.controller.ProfileController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Created by ZotovES on 05.04.2020
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringContextConfig.class);
        context.getBean(ActivityController.class);
        context.getBean(ProfileController.class);
        context.getBean(AnalyticController.class);
    }
}
