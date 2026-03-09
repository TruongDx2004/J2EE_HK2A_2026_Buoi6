package restaurant.project.order_table.model;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(min = 3, message = "Tiêu đề phải có ít nhất 3 ký tự")
    private String title;

    @NotNull(message = "Giá không được để trống")
    @Min(value = 0, message = "Giá phải lớn hơn hoặc bằng 0")
    @Max(value = 1000000, message = "Giá phải nhỏ hơn hoặc bằng 1,000,000")
    @Column(nullable = false)
    private Double price;

    @NotBlank(message = "Tác giả không được để trống")
    @Column(nullable = false)
    private String author;

    @Length(min = 0, max = 255, message = "Đường dẫn ảnh phải có độ dài từ 0 đến 255 ký tự")
    @Column(length = 255)
    private String image;

    @NotNull(message = "Vui lòng chọn danh mục")
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
