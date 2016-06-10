package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.model.products.Picture;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Slawek on 12/03/16.
 */
public class LightBox implements Cloneable{

    private String name;
    private Client owner;
    private List<Picture> items = new LinkedList<>();
    private boolean closed = false;
    private String number;

    public LightBox(Client owner){
        this.owner = owner;
    }

    public void close(){
        this.closed = true;
    }

    public void changeName(String newName){
        validate();
        this.name = newName;
    }

    /*
    public - modyfukiator dostępu
    static - opcjonanie aby metoda na rzecz klasy a nie obiektou
    void - typ zwracany, bez zwracania
    remove - nazwenik metody
    () - parametry
          Picture - typ
          pictureToRemove - nazewnik parametru
     */
    public void remove(Picture pictureToRemove){
        validate();
        boolean removed = items.remove(pictureToRemove);
        if (!removed)
            throw new IllegalArgumentException("does not contain");
    }

    public void add(Picture pictureToAdd) throws IllegalStateException, IllegalArgumentException{
       validate();
        if (items.contains(pictureToAdd))
            throw new IllegalArgumentException("already contains");
        items.add(pictureToAdd);
    }

    private void validate(){
        if (closed)
            throw new IllegalStateException("closed!");
        if (!owner.isActive())
            throw new IllegalStateException("owner is not active!");
    }
/*
    public void add2(Picture pictureToAdd) throws IllegalStateException, IllegalArgumentException{
        //sprawdzamy czy items zawiera już ten picture
        //for (int coursor = 0; coursor < items.length; coursor++){
        for(Picture pic : items){
            //Picture pic = items[coursor];
            if (pic != null){
                String nr1 = pic.getNumber();
                String nr2 = pictureToAdd.getNumber();
                if (nr1.equals(nr2)){
                    throw new IllegalArgumentException("lightbox already contains picture " + pictureToAdd.getNumber());
                }
            }
        }
        //dodaje go jezeli znajdzie puste miejsce

        boolean added = false;
        int coursor = 0;
        //for (int coursor = 0; coursor < items.length; coursor++){
        for(Picture reference : items){
            //Picture reference = items[coursor];
            if (reference == null){
                //reference = pictureToAdd; inne miejsce niż krakta tablicy!
                items[coursor] = pictureToAdd;
                added = true;
                break;//break  !!!!!
            }
            coursor++;
        }
        if(!added)
            throw new IllegalStateException("Lightbox przepełniony!!!");
    }
*/
    public String getName() {
        return name;
    }

    public int getItemsCount() {
        return items.size();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        LightBox result = (LightBox) super.clone();
        result.items = (List)((LinkedList)items).clone();
        return result;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<Picture> getItems() {
        return items;
    }

    public Client getOwner() {
        return owner;
    }
}
