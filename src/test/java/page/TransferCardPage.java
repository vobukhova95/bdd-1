package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferCardPage {
    private final SelenideElement amount = $("[data-test-id='amount'] input");
    private final SelenideElement cardFrom = $("[data-test-id='from'] input");
    private final SelenideElement cardTo = $("[data-test-id='to'] input");
    private final SelenideElement actionTransfer = $("[data-test-id='action-transfer']");
    private final SelenideElement actionCancel = $("[data-test-id='action-cancel']");
    private final SelenideElement header = $("h1");

    public TransferCardPage() {
        header.shouldHave(Condition.exactText("Пополнение карты"), Condition.visible);
    }

    public DashBoardPage transferAmount(DataHelper.CardInfo fromCard, String amountTransfer) {
        amount.setValue(amountTransfer);
        cardFrom.setValue(fromCard.getCardNumber());
        actionTransfer.click();
        return new DashBoardPage();
    }

    //проверка, что в поле "Куда" лежит выбранная для пополнения карта
    public String getCardTo() {
        String text = cardTo.getValue();
        return text.substring(15);
    }


}
