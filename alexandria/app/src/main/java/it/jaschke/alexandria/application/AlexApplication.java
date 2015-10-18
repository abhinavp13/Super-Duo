package it.jaschke.alexandria.application;

import android.app.Application;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import it.jaschke.alexandria.R;


/**
 * This is Application Class for whole app.
 * ACRA initiation is most important task here.
 * ACRA is an error reporting app.
 * Currently, I don't have a web domain where I can point the stack trace of the
 * error. So I linked to my registered email.
 *
 * Created by Abhinav Puri.
 */
@ReportsCrashes(
        mailTo = "pabhinav@iitrpr.ac.in",
        mode = ReportingInteractionMode.DIALOG,
        resToastText = R.string.crash_toast_text,
        resDialogText = R.string.crash_dialog_text,
        resDialogIcon = android.R.drawable.ic_dialog_info,
        resDialogTitle = R.string.crash_dialog_title,
        resDialogCommentPrompt = R.string.crash_dialog_comment_prompt,
        resDialogOkToast = R.string.crash_dialog_ok_toast
)
public class AlexApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /*
         * The following line triggers the initialization of ACRA.
         * Right now, ACRA simply ask user to send mail to developer, becoz its just a debugging stage.
         * Can be changed to send crash messages to a specific server, later in release stage.
         */
        ACRA.init(this);

    }
}