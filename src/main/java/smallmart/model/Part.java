package smallmart.model;

public class Part {
    //    private String prodId;
//    private String count;
    private Integer prodId;
    private Integer count;

//    public Part(String prodId, String count) {
//        this.prodId = prodId;
//        this.count = count;
//    }
//
//    public Part() {
//    }
//
//    public String getProdId() {
//        return prodId;
//    }
//
//    public void setProdId(String prodId) {
//        this.prodId = prodId;
//    }
//
//    public String getCount() {
//        return count;
//    }
//
//    public void setCount(String count) {
//        this.count = count;
//    }

    public Part(Integer prodId, Integer count) {
        this.prodId = prodId;
        this.count = count;
    }

    public Part() {
    }

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
