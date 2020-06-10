package org.evan.controller;

import org.evan.service.ItemsService;
import org.evan.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/6/1 0001
 * Time: 21:52
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 */
@ApiIgnore
@RestController
@RequestMapping("data")
public class DataController {

    @Autowired
    private ItemsService itemsService;


    @PostMapping("parseJson")
    public Result parseData(@RequestBody String jsonData){
        itemsService.parseData(jsonData);
        return Result.success();
    }

}
