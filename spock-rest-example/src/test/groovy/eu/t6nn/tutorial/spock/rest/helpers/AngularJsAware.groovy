package eu.t6nn.tutorial.spock.rest.helpers

/**
 * @author tonispi
 */
trait AngularJsAware {
    boolean isAngularReady() {

        js.exec('window.WALLET.waitForAngular();');
        waitFor {
            js.WALLET.APP_READY == true
        }

    }
}