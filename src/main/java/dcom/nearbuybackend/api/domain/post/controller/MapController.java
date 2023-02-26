package dcom.nearbuybackend.api.domain.post.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Map Controller"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MapController {
}
