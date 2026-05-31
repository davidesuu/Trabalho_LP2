package Entity;

public class Vinculo {
        private Long ppcId;
        private String situacao;
        private int chTotalCumprida;

        public Vinculo(Long ppcId, String situacao, int chTotalCumprida) {
                this.ppcId = ppcId;
                this.situacao = situacao;
                this.chTotalCumprida = chTotalCumprida;
        }

        public void setChTotalCumprida(int chTotalCumprida) {
                this.chTotalCumprida = chTotalCumprida;
        }

        public int getChTotalCumprida() {
                return chTotalCumprida;
        }

        public Long getPpcId() {
                return ppcId;
        }

        public void setPpcId(Long ppcId) {
                this.ppcId = ppcId;
        }
}
