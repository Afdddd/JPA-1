package jpabook.jpashop.domain.item;

import jpabook.jpashop.controller.AlbumForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Getter
@Setter
public class Album extends Item{
    private dtype type = dtype.A;

    private String artist;
    private String etc;

    public static Album createAlbum(AlbumForm form) {
        Album album = new Album();

        album.setName(form.getName());
        album.setPrice(form.getPrice());
        album.setStockQuantity(form.getStockQuantity());
        album.setArtist(form.getArtist());
        album.setEtc(form.getEtc());
        return album;
    }
}
