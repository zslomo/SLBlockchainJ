package science.bintan.blockchain.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lomo on 2017/10/11.
 */
@ConfigurationProperties(prefix = "blockchain")
public class BlockchainProperties {

    private List<BcInfo> bcInfos = new ArrayList<>();

    public List<BcInfo> getBcInfos() {
        return bcInfos;
    }

    public void setBcInfos(List<BcInfo> bcInfos) {
        this.bcInfos = bcInfos;
    }

    public static class BcInfo {

        private String name;

        private String clientUrl;

        private String account;

        private String password;



        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClientUrl() {
            return clientUrl;
        }

        public void setClientUrl(String clientUrl) {
            this.clientUrl = clientUrl;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }
}
