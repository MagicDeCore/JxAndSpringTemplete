package app;

import app.controllers.AddUserController;
import app.controllers.DetailsUserController;
import app.controllers.EditUserController;
import app.controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

/**
 * Date: 27.08.15
 * Time: 11:04
 *
 * @author Ruslan Molchanov (ruslanys@gmail.com)
 * @author http://mruslan.com
 */
@Configuration
public class ControllersConfiguration {

    @Bean(name = "mainView")
    public ViewHolder getMainView() throws IOException {
        return loadView("fxml/mainView.fxml");
    }

    @Bean(name = "addUserView")
    public ViewHolder getAddUserView() throws IOException {
        return loadView("fxml/addUser.fxml");
    }

    @Bean(name = "editUserView")
    public ViewHolder getEditUserView() throws IOException {
        return loadView("fxml/editUser.fxml");
    }

    @Bean(name = "detailUserView")
    public ViewHolder getDetailsUserView() throws IOException {
        return loadView("fxml/detailsUser.fxml");
    }
    /**
     * Именно благодаря этому методу мы добавили контроллер в контекст спринга,
     * и заставили его сделать произвести все необходимые инъекции.
     */
    @Bean
    public MainController getLoginController() throws IOException {
        return (MainController) getMainView().getController();
    }

    @Bean
    public AddUserController getAddUserController() throws IOException {
        return (AddUserController) getAddUserView().getController();
    }

    @Bean
    public EditUserController getEditUserController() throws IOException {
        return (EditUserController) getEditUserView().getController();
    }

    @Bean
    public DetailsUserController getDetailsController() throws IOException {
        return (DetailsUserController) getDetailsUserView().getController();
    }

    /**
     * Самый обыкновенный способ использовать FXML загрузчик.
     * Как раз-таки на этом этапе будет создан объект-контроллер,
     * произведены все FXML инъекции и вызван метод инициализации контроллера.
     */
    public ViewHolder loadView(String url) throws IOException {
        InputStream fxmlStream = null;
        try {
            fxmlStream = getClass().getClassLoader().getResourceAsStream(url);
            FXMLLoader loader = new FXMLLoader();
            loader.load(fxmlStream);
            return new ViewHolder(loader.getRoot(), loader.getController());
        } finally {
            if (fxmlStream != null) {
                fxmlStream.close();
            }
        }
    }

    /**
     * Класс - оболочка: контроллер мы обязаны указать в качестве бина,
     * а view - представление, нам предстоит использовать в точке входа {@link Application}.
     */
    public class ViewHolder {
        private Parent view;
        private Object controller;

        public ViewHolder(Parent view, Object controller) {
            this.view = view;
            this.controller = controller;
        }

        public Parent getView() {
            return view;
        }

        public void setView(Parent view) {
            this.view = view;
        }

        public Object getController() {
            return controller;
        }

        public void setController(Object controller) {
            this.controller = controller;
        }
    }

}
