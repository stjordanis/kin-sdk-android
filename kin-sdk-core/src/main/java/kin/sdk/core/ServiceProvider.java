package kin.sdk.core;

import static java.lang.annotation.RetentionPolicy.SOURCE;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import java.lang.annotation.Retention;
import org.stellar.sdk.Asset;
import org.stellar.sdk.AssetTypeCreditAlphaNum;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.responses.AccountResponse;

public class ServiceProvider {

    /**
     * main horizon network
     */
    public static final int NETWORK_ID_MAIN = 1;
    /**
     * test horizon network
     */
    public static final int NETWORK_ID_TEST = 2;

    private static final String MAIN_NETWORK_ISSUER = "GBGFNADX2FTYVCLDCVFY5ZRTVEMS4LV6HKMWOY7XJKVXMBIWVDESCJW5";
    private static final String TEST_NETWORK_ISSUER = "GBA2XHZRUAHEL4DZX7XNHR7HLBAUYPRNKLD2PIUKWV2LVVE6OJT4NDLM";

    private final String providerUrl;
    private final int networkId;
    private final KinAsset kinAsset;

    @Retention(SOURCE)
    @IntDef({NETWORK_ID_MAIN, NETWORK_ID_TEST})
    public @interface NetworkId {

    }

    /**
     * A ServiceProvider used to connect to an horizon network.
     * <p>
     *
     * @param providerUrl the provider to use
     * @param networkId for example see {@value #NETWORK_ID_MAIN} {@value NETWORK_ID_TEST}
     */
    public ServiceProvider(String providerUrl, @NetworkId int networkId) {
        this.providerUrl = providerUrl;
        this.networkId = networkId;
        this.kinAsset = new KinAsset(isMainNet() ? MAIN_NETWORK_ISSUER : TEST_NETWORK_ISSUER);
    }

    public String getProviderUrl() {
        return providerUrl;
    }

    public int getNetworkId() {
        return networkId;
    }

    public boolean isMainNet() {
        return networkId == NETWORK_ID_MAIN;
    }

    KinAsset getKinAsset() {
        return kinAsset;
    }

    static class KinAsset {

        private static final String KIN_ASSET_CODE = "KIN";
        private final AssetTypeCreditAlphaNum stellarKinAsset;

        KinAsset(String kinIssuerAccountId) {
            KeyPair issuerKeyPair = KeyPair.fromAccountId(kinIssuerAccountId);
            this.stellarKinAsset = (AssetTypeCreditAlphaNum) Asset.createNonNativeAsset(KIN_ASSET_CODE, issuerKeyPair);
        }

        boolean isKinBalance(@NonNull AccountResponse.Balance balance) {
            return stellarKinAsset.getCode().equals(balance.getAssetCode()) &&
                balance.getAssetIssuer() != null &&
                stellarKinAsset.getIssuer().getAccountId().equals(balance.getAssetIssuer().getAccountId());
        }

        @NonNull
        Asset getStellarAsset() {
            return stellarKinAsset;
        }
    }
}