package marketflow.econ;

public class Transaction
{
    private int _id;
    private String _resource;
    private int _price;
    public Transaction(int id, String rid, int price)
    {
        _id=id;
        _resource=rid;
        _price=price;
    }
    public int ID() { return _id; }
    public String Resource()
    {
        return _resource;
    }
    public int Price() { return _price;}
}
