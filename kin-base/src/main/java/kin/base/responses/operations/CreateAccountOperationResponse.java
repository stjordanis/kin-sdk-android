package kin.base.responses.operations;

import com.google.gson.annotations.SerializedName;

import kin.base.KeyPair;
import kin.base.Server;

/**
 * Represents CreateAccount operation response.
 * @see <a href="https://www.stellar.org/developers/horizon/reference/resources/operation.html" target="_blank">Operation documentation</a>
 * @see kin.base.requests.OperationsRequestBuilder
 * @see Server#operations()
 */
public class CreateAccountOperationResponse extends OperationResponse {
  @SerializedName("account")
  protected final KeyPair account;
  @SerializedName("funder")
  protected final KeyPair funder;
  @SerializedName("starting_balance")
  protected final String startingBalance;

  CreateAccountOperationResponse(KeyPair funder, String startingBalance, KeyPair account) {
    this.funder = funder;
    this.startingBalance = startingBalance;
    this.account = account;
  }

  public KeyPair getAccount() {
    return account;
  }

  public String getStartingBalance() {
    return startingBalance;
  }

  public KeyPair getFunder() {
    return funder;
  }
}
