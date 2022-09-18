package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.DiscriminatorValue;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemFormController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String selectItem() {
        return "items/selectItem";
    }

    @GetMapping("/items/newBook")
    public String createBookForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createBookForm";
    }

    @PostMapping("/items/newBook")
    public String createBook(BookForm form) {

        Book book = Book.createBook(form);
        itemService.savedItem(book);
        return "redirect:/";

    }

    @GetMapping("/items/newAlbum")
    public String createAlbumForm(Model model) {
        model.addAttribute("form", new AlbumForm());
        return "items/createAlbumForm";
    }

    @PostMapping("/items/newAlbum")
    public String createAlbum(@ModelAttribute("form") AlbumForm form) {
        Album album = Album.createAlbum(form);
        itemService.savedItem(album);
        return "redirect:/";
    }

    @GetMapping("/items/newMovie")
    public String createMovieForm(Model model) {
        model.addAttribute("form",new MovieForm());
        return "items/createMovieForm";
    }

    @PostMapping("/items/newMovie")
    public String createMovie(@ModelAttribute("form") MovieForm form) {
        Movie movie = Movie.createMovie(form);
        itemService.savedItem(movie);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {

        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item =  itemService.findOne(itemId);
            Book book = (Book)item;
            BookForm form = new BookForm();
            form.setId(book.getId());
            form.setName(book.getName());
            form.setPrice(book.getPrice());
            form.setStockQuantity(book.getStockQuantity());
            form.setAuthor(book.getAuthor());
            form.setIsbn(book.getIsbn());
            model.addAttribute("form", form);
            return "items/updateItemForm";

    }

/**
 *  merge말고 변경감지를 사용
 */
    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId ,@ModelAttribute("form") BookForm form) {
//        Book book = new Book();
//        book.setId(form.getId());
//        book.setName(form.getName());
//        book.setPrice(form.getPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn());
//        itemService.savedItem(book);

        itemService.updateItem(form.getId(),form.getName(),form.getPrice());
        return "redirect:/items";

    }
}
