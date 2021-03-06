package topworker.view.naviagtion.download;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import topworker.utils.MessagesBundle;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Created by echomil on 21.03.16.
 */

@SpringView(name = Download.VIEW_NAME)
public class Download extends VerticalLayout implements View {

    public static final String VIEW_NAME = "download";
    private final String CLIENT_PATH = System.getProperty("user.home") + "/app-root/data/Client.zip";
    private VerticalLayout content;
    private Label downloadLabel;
    private Locale currentLocale;

    @Autowired
    private MessagesBundle messagesBundle;

    public Download() {


    }

    @PostConstruct
    private void init() {
        createComponents();
        addComponent(downloadLabel);
        addComponent(content);
        setExpandRatio(downloadLabel, 1.5f);
        setExpandRatio(downloadLabel, 9f);
        setComponentAlignment(downloadLabel, Alignment.MIDDLE_CENTER);
        setComponentAlignment(content, Alignment.TOP_CENTER);
        currentLocale = messagesBundle.getLocale();

    }

    private void createComponents() {
        content = new VerticalLayout();
        TextArea instruction = new TextArea();
        instruction.setEnabled(false);
        instruction.addStyleName("information");
        instruction.setValue(messagesBundle.getMessage("instalation_instruction"));
        instruction.setSizeFull();
        Button button = new Button(messagesBundle.getMessage("download_btn_caption"));
        StreamResource resource = createResource();
        FileDownloader fileDownloader = new FileDownloader(resource);
        fileDownloader.extend(button);
        content.addComponent(instruction);
        content.addComponent(button);
        content.setWidth(40f, Unit.PERCENTAGE);
        content.setHeight(100f, Unit.PERCENTAGE);
        content.setExpandRatio(instruction, 1.5f);
        content.setExpandRatio(button, 9f);
        content.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        content.setComponentAlignment(instruction, Alignment.MIDDLE_CENTER);
        downloadLabel = new Label(messagesBundle.getMessage("download_label"));
        downloadLabel.addStyleName("download-label");
    }

    private StreamResource createResource() {
        return new StreamResource(new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                try {
                    return new FileInputStream(CLIENT_PATH);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, "WorkTimeClient.zip");


    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (!currentLocale.equals(messagesBundle.getLocale())) {
            removeAllComponents();
            init();
        }
    }
}
