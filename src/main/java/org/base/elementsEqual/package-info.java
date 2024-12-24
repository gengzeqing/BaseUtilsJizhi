package org.base.elementsEqual;
/**
 * 判断两个集合对象是否相同
 * 使用 Iterables.elementsEqual() 工具类
 * 判断两个集合是否相等，需要重写对象的 equals()  hashCode() 方法
 *
 * List<ChannelTrajectoryVo> filingFileList = Lists.newArrayList();
 *
 * // 分组后的新的文件信息
 * Map<Integer, List<ChannelFileVo>> filingFileNewMap = filingFileNew.stream().collect(Collectors.groupingBy(o -> o.getFileType()));
 * // 分组后的旧的文件信息
 * Map<Integer, List<ChannelFileVo>> filingFileOldMap = filingFileOld.stream().collect(Collectors.groupingBy(o -> o.getFileType()));
 *
 * filingFileOldMap.forEach((k, y) -> {
 * 	filingFileNewMap.forEach((k1, y1) -> {
 * 		if (k.equals(k1)) {
 * 			// 查询两个集合是否相同
 * 			if (!Iterables.elementsEqual(y, y1)) {
 * 				log.info("比对修改的图片:{} {} ",y,y1);
 * 				List<String> collectlod = y.stream().map(ChannelFileVo::getUrl).collect(Collectors.toList());
 * 				List<String> collectNew = y1.stream().map(ChannelFileVo::getUrl).collect(Collectors.toList());
 * 				filingFileList.add(new ChannelTrajectoryVo(map.get(k), String.join(",", collectNew), String.join(",", collectlod),Constants.ONE));
 * 			   }
 * 		}
 * 	});
 * });
 */

