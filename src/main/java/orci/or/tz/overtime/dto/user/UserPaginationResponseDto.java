package orci.or.tz.overtime.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserPaginationResponseDto {
    private int currentPage;
    private int totalItems;
    private int totalPages;
    private int pageSize; 
    private List<UserResponseDto> data;
}
