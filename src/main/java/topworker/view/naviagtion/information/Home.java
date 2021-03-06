package topworker.view.naviagtion.information;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import org.springframework.beans.factory.annotation.Autowired;
import topworker.utils.MessagesBundle;

import javax.annotation.PostConstruct;

/**
 * Created by echomil on 07.03.16.
 */
@SpringView(name = Home.VIEW_NAME)
public class Home extends SimpleInformationPage {

    public final static String VIEW_NAME = "";

    public Home() {
        super();
    }

    @Autowired
    private MessagesBundle messagesBundle;

    @PostConstruct
    private void init() {
        setTitleLabel(messagesBundle.getMessage("home_label"));
        setInformationMessage(messagesBundle.getMessage("home_information"));
        currentLocale = messagesBundle.getLocale();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        setTitleLabel(messagesBundle.getMessage("home_label"));
        setInformationMessage(messagesBundle.getMessage("home_information"));
    }
}
