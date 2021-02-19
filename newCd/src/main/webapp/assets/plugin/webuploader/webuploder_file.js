jQuery(function() {
	var $ = jQuery, // just in case. Make sure it's not an other libaray.
	$wrap = $('#uploader'),
	// 图片容器
	$queue = $('<ul class="filelist"></ul>').appendTo($wrap.find('.queueList')),

	// 状态栏，包括进度和控制按钮
	$statusBar = $wrap.find('.statusBar'),

	// 文件总体选择信息。
	$info = $statusBar.find('.info'),

	// 上传按钮
	$upload = $wrap.find('.uploadBtn'),

	// 没选择文件之前的内容。
	$placeHolder = $wrap.find('.placeholder'),

	// 总体进度条
	$progress = $statusBar.find('.progress').hide(),

	// 添加的文件数量
	fileCount = 0,

	// 添加的文件总大小
	fileSize = 0,

	// 优化retina, 在retina下这个值是2
	ratio = window.devicePixelRatio || 1,

	// 缩略图大小
	thumbnailWidth = 110 * ratio, thumbnailHeight = 110 * ratio,

	// 可能有pedding, ready, uploading, confirm, done.
	state = 'pedding',

	// 所有文件的进度信息，key为file id
	percentages = {},

	supportTransition = (function() {
		var s = document.createElement('p').style, r = 'transition' in s
				|| 'WebkitTransition' in s || 'MozTransition' in s
				|| 'msTransition' in s || 'OTransition' in s;
		s = null;
		return r;
	})(),

	// WebUploader实例
	uploader;

	if (!WebUploader.Uploader.support()) {
		alert('Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
		throw new Error(
				'WebUploader does not support the browser you are using.');
	}

	// 实例化 a:无限制 w:word i:image e:excel 默认无限制：a
	if (fileType == 'i') {
		uploader = WebUploader.create({
			pick : {
				id : '#filePicker',
				label : '点击选择文件'
			},
			dnd : '#uploader .queueList',
			paste : document.body,
			accept : {
				title : 'Images',
				extensions : 'gif,jpg,jpeg,bmp,png',
				mimeTypes : 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
			},
			// swf文件路径
			swf : ctx + '/assets/js/plugins/webuploader/Uploader.swf',

			disableGlobalDnd : true,

			chunked : false,
			server : ctx + '/attach/upload/doFileUpload',
			fileNumLimit : attachNum,
			fileSizeLimit : attachNum * 50 * 1024 * 1024, // 50 M
			fileSingleSizeLimit : 50 * 1024 * 1024
		// 50 M
		});
	} else if(fileType == 'w'){
		uploader = WebUploader.create({
			pick : {
				id : '#filePicker',
				label : '点击选择文件'
			},
			dnd : '#uploader .queueList',
			paste : document.body,
			accept : {
				title : 'Doc',
				extensions : 'doc,docx',
				mimeTypes : 'application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document'
			},
			// swf文件路径
			swf : ctx + '/assets/js/plugins/webuploader/Uploader.swf',

			disableGlobalDnd : true,

			chunked : false,
			server : ctx + '/attach/upload/doFileUpload',
			fileNumLimit : attachNum,
			fileSizeLimit : attachNum * 50 * 1024 * 1024, // 50 M
			fileSingleSizeLimit : 50 * 1024 * 1024
		// 50 M
		});
	}else if(fileType == 'e'){
		uploader = WebUploader.create({
			pick : {
				id : '#filePicker',
				label : '点击选择文件'
			},
			dnd : '#uploader .queueList',
			paste : document.body,
			accept : {
				title : 'Excel',
				extensions : 'xls,xlsx',
				mimeTypes : 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
			},
			// swf文件路径
			swf : ctx + '/assets/js/plugins/webuploader/Uploader.swf',

			disableGlobalDnd : true,

			chunked : false,
			server : ctx + '/attach/upload/doFileUpload',
			fileNumLimit : attachNum,
			fileSizeLimit : attachNum * 50 * 1024 * 1024, // 50 M
			fileSingleSizeLimit : 50 * 1024 * 1024
		// 50 M
		});
	}else if(fileType == 'pv'){
		uploader = WebUploader.create({
			pick : {
				id : '#filePicker',
				label : '点击选择文件'
			},
			dnd : '#uploader .queueList',
			paste : document.body,
			accept : {
				title : 'PV',
				extensions : 'ppt,pptx,mp4,ogg,webm',
				mimeTypes : 'application/powerpoint,application/vnd.openxmlformats-officedocument.presentationml.presentation,video/mp4,video/webm,video/ogg'
			},
			// swf文件路径
			swf : ctx + '/assets/js/plugins/webuploader/Uploader.swf',

			disableGlobalDnd : true,

			chunked : false,
			server : ctx + '/attach/upload/doFileUpload',
			fileNumLimit : attachNum,
			fileSizeLimit : attachNum * 50 * 1024 * 1024, // 50 M
			fileSingleSizeLimit : 50 * 1024 * 1024
		// 50 M
		});
	}else if(fileType == 'ppt'){
		uploader = WebUploader.create({
			pick : {
				id : '#filePicker',
				label : '点击选择文件'
			},
			dnd : '#uploader .queueList',
			paste : document.body,
			accept : {
				title : 'PPT',
				extensions : 'ppt,pptx',
				mimeTypes : 'application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.presentationml.presentation'
			},
			// swf文件路径
			swf : ctx + '/assets/js/plugins/webuploader/Uploader.swf',

			disableGlobalDnd : true,

			chunked : false,
			server : ctx + '/attach/upload/doFileUpload',
			fileNumLimit : attachNum,
			fileSizeLimit : attachNum * 50 * 1024 * 1024, // 50 M
			fileSingleSizeLimit : 50 * 1024 * 1024
		// 50 M
		});
	}else if(fileType == 'v'){
		uploader = WebUploader.create({
			pick : {
				id : '#filePicker',
				label : '点击选择文件'
			},
			dnd : '#uploader .queueList',
			paste : document.body,
			accept : {
				title : 'VIDEO',
				extensions : 'mp4,flv,avi,wmv,rmvb',
				mimeTypes : 'video/mp4,video/x-flv,video/x-msvideo,video/x-ms-wmv,application/vnd.rn-realmedia-vbr'
			},
			// swf文件路径
			swf : ctx + '/assets/js/plugins/webuploader/Uploader.swf',
			
			disableGlobalDnd : true,
			
			chunked : false,
			server : ctx + '/attach/upload/doFileUpload',
			fileNumLimit : attachNum,
			fileSizeLimit : attachNum * 300 * 1024 * 1024, // 50 M
			fileSingleSizeLimit : 300 * 1024 * 1024
			// 50 M
		});
	}else{
		uploader = WebUploader.create({
			pick : {
				id : '#filePicker',
				label : '点击选择文件'
			},
			dnd : '#uploader .queueList',
			paste : document.body,
			// swf文件路径
			swf : ctx + '/assets/js/plugins/webuploader/Uploader.swf',
			// accept :{},
			disableGlobalDnd : true,
			chunked : false,
			server : ctx + '/attach/upload/doFileUpload',
			fileNumLimit : attachNum,
			fileSizeLimit : 5 * 1024 * 1024 * 1024, // 5G
			fileSingleSizeLimit : 5 * 1024 * 1024 * 1024
		});
	}
	
	console.log(fileType);

	// 添加“添加文件”的按钮，
	var selectNum = uploader.getStats();
	if ((selectNum.successNum + selectNum.progressNum + selectNum.queueNum + selectNum.interruptNum) < attachNum) {
		uploader.addButton({
			id : '#filePicker2',
			label : '继续添加'
		});
	}

	// 当有文件添加进来时执行，负责view的创建
	function addFile(file) {
		var $li = $('<li id="' + file.id + '">' + '<p class="title">'
				+ file.name + '</p>' + '<p class="imgWrap"></p>'
				+ '<p class="progress"><span></span></p>' + '</li>'),

		$btns = $(
				'<div class="file-panel">' + '<span class="cancel">删除</span>'
						+ '<span class="rotateRight">向右旋转</span>'
						+ '<span class="rotateLeft">向左旋转</span></div>')
				.appendTo($li), $prgress = $li.find('p.progress span'), $wrap = $li
				.find('p.imgWrap'), $info = $('<p class="error"></p>'),

		showError = function(code) {
			console.log(code);
			switch (code) {
			case 'exceed_size':
				text = '文件大小超出';
				break;

			case 'interrupt':
				text = '上传暂停';
				break;

			default:
				text = '上传失败，请重试';
				break;
			}

			$info.text(text).appendTo($li);
		};

		if (file.getStatus() === 'invalid') {
			showError(file.statusText);
		} else {
			// @todo lazyload
			$wrap.text('预览中');
			uploader.makeThumb(file, function(error, src) {
				if (error) {
					$wrap.text('非图片不能预览');
					return;
				}

				var img = $('<img src="' + src + '">');
				$wrap.empty().append(img);
			}, thumbnailWidth, thumbnailHeight);

			percentages[file.id] = [ file.size, 0 ];
			file.rotation = 0;
		}

		file.on('statuschange', function(cur, prev) {
			if (prev === 'progress') {
				$prgress.hide().width(0);
			} else if (prev === 'queued') {
				$li.off('mouseenter mouseleave');
				$btns.remove();
			}

			// 成功
			if (cur === 'error' || cur === 'invalid') {
				// console.log( file.statusText );
				showError(file.statusText);
				percentages[file.id][1] = 1;
			} else if (cur === 'interrupt') {
				showError('interrupt');
			} else if (cur === 'queued') {
				percentages[file.id][1] = 0;
			} else if (cur === 'progress') {
				$info.remove();
				$prgress.css('display', 'block');
			} else if (cur === 'complete') {
				$li.append('<span class="success"></span>');
			}

			$li.removeClass('state-' + prev).addClass('state-' + cur);
		});

		$li.on('mouseenter', function() {
			$btns.stop().animate({
				height : 30
			});
		});

		$li.on('mouseleave', function() {
			$btns.stop().animate({
				height : 0
			});
		});

		$btns
				.on(
						'click',
						'span',
						function() {
							var index = $(this).index(), deg;

							switch (index) {
							case 0:
								uploader.removeFile(file);
								return;

							case 1:
								file.rotation += 90;
								break;

							case 2:
								file.rotation -= 90;
								break;
							}

							if (supportTransition) {
								deg = 'rotate(' + file.rotation + 'deg)';
								$wrap.css({
									'-webkit-transform' : deg,
									'-mos-transform' : deg,
									'-o-transform' : deg,
									'transform' : deg
								});
							} else {
								$wrap
										.css(
												'filter',
												'progid:DXImageTransform.Microsoft.BasicImage(rotation='
														+ (~~((file.rotation / 90) % 4 + 4) % 4)
														+ ')');
								// use jquery animate to rotation
								// $({
								// rotation: rotation
								// }).animate({
								// rotation: file.rotation
								// }, {
								// easing: 'linear',
								// step: function( now ) {
								// now = now * Math.PI / 180;

								// var cos = Math.cos( now ),
								// sin = Math.sin( now );

								// $wrap.css( 'filter',
								// "progid:DXImageTransform.Microsoft.Matrix(M11="
								// + cos + ",M12=" + (-sin) + ",M21=" + sin +
								// ",M22=" + cos + ",SizingMethod='auto
								// expand')");
								// }
								// });
							}

						});

		$li.appendTo($queue);
	}

	// 负责view的销毁
	function removeFile(file) {
		var $li = $('#' + file.id);

		delete percentages[file.id];
		updateTotalProgress();
		$li.off().find('.file-panel').off().end().remove();
	}

	function updateTotalProgress() {
		var loaded = 0, total = 0, spans = $progress.children(), percent;

		$.each(percentages, function(k, v) {
			total += v[0];
			loaded += v[0] * v[1];
		});

		percent = total ? loaded / total : 0;

		spans.eq(0).text(Math.round(percent * 100) + '%');
		spans.eq(1).css('width', Math.round(percent * 100) + '%');
		updateStatus();
	}

	function updateStatus() {
		var text = '', stats;

		if (state === 'ready') {
			text = '选中' + fileCount + '个文件，共'
					+ WebUploader.formatSize(fileSize) + '。';
		} else if (state === 'confirm') {
			stats = uploader.getStats();
			if (stats.uploadFailNum) {
				text = '已成功上传'
						+ stats.successNum
						+ '个文件，'
						+ stats.uploadFailNum
						+ '个文件上传失败，<a class="retry" href="#">重新上传</a>失败文件'
			}
//			+ '个文件上传失败，<a class="retry" href="#">重新上传</a>失败文件或<a class="ignore" href="#">忽略</a>'

		} else {
			stats = uploader.getStats();
			text = '共' + fileCount + '个（' + WebUploader.formatSize(fileSize)
					+ '），已上传' + stats.successNum + '个';

			if (stats.uploadFailNum) {
				text += '，失败' + stats.uploadFailNum + '个';
			}
		}

		$info.html(text);
	}

	function setState(val) {
		var file, stats;

		if (val === state) {
			return;
		}

		$upload.removeClass('state-' + state);
		$upload.addClass('state-' + val);
		state = val;

		switch (state) {
		case 'pedding':
			$placeHolder.removeClass('element-invisible');
			$queue.parent().removeClass('filled');
			$queue.hide();
			$statusBar.addClass('element-invisible');
			uploader.refresh();
			break;

		case 'ready':
			$placeHolder.addClass('element-invisible');
			$('#filePicker2').removeClass('element-invisible');
			$queue.parent().addClass('filled');
			$queue.show();
			$statusBar.removeClass('element-invisible');
			uploader.refresh();
			break;

		case 'uploading':
			$('#filePicker2').addClass('element-invisible');
			$progress.show();
			$upload.text('暂停上传');
			break;

		case 'paused':
			$progress.show();
			$upload.text('继续上传');
			break;

		case 'confirm':
			$progress.hide();
			$upload.text('开始上传').addClass('disabled');

			stats = uploader.getStats();
			if (stats.successNum && !stats.uploadFailNum) {
				setState('finish');
				return;
			}
			break;
		case 'finish':
			// console.log();
			stats = uploader.getStats();
			// console.log(stats);
			if (stats.successNum) {
				// alert( '上传成功' );

			} else {
				// 没有成功的图片，重设
				state = 'done';
				location.reload();
			}
			break;
		}

		updateStatus();
	}

	uploader.onUploadProgress = function(file, percentage) {
		var $li = $('#' + file.id), $percent = $li.find('.progress span');

		$percent.css('width', percentage * 100 + '%');
		percentages[file.id][1] = percentage;
		updateTotalProgress();
	};

	uploader.onFileQueued = function(file) {
		fileCount++;
		fileSize += file.size;

		if (fileCount === 1) {
			$placeHolder.addClass('element-invisible');
			$statusBar.show();
		}

		addFile(file);
		setState('ready');
		updateTotalProgress();
	};

	uploader.onFileDequeued = function(file) {
		fileCount--;
		fileSize -= file.size;

		if (!fileCount) {
			setState('pedding');
		}

		removeFile(file);
		updateTotalProgress();

	};

	uploader.on('all', function(type) {
		var stats;
		switch (type) {
		case 'uploadFinished':
			setState('confirm');
			break;

		case 'startUpload':
			setState('uploading');
			break;

		case 'stopUpload':
			setState('paused');
			break;

		}
	});
	/* 获取服务器返回的数据 */
	uploader.on('uploadAccept', function(file, result) {
		if (result.state == "ok") {
			var data = result.data;
			/**
			 * 文件上传回调
			 * 
			 * @param attachField
			 *            附件对应的id名称
			 * @param attachDivId
			 *            附件上一层最大的divId
			 * @param attachId
			 *            本次上传获取的附件id
			 * @param attachFileName
			 *            本次上传获取的附件名称
			 * @param attachFileSuffix
			 *            本次上传获取的附件后缀名
			 * @param attachNum
			 *            附件的最大个数 如果值为1，则默认是单张上传
			 * @param fileType
			 *            Y:是图片 N：不是图片 ，不填默认为文件（非图片）
			 * @param position
			 *            位置 m:中部 b:底部 默认中部
			 * @returns
			 */
			parent.uploadFileComplete(attachField, attachDivId, data.attachId,
					data.fileName, data.fileSuffix, attachNum, fileType,
					position);

			// 如果上传完毕，则关闭弹窗
			if (fileCount - 1 == uploader.getStats().successNum) {
				// 关闭弹窗
				$("#filesModal").modal('hide'); // 手动关闭
				$("#filesModal").remove();// 清除页面
			}
		}
	});

	uploader.onError = function(code) {
		if (code == 'Q_EXCEED_NUM_LIMIT') {
			alert("超出可上传文件数！")
		}else if(code == 'Q_EXCEED_SIZE_LIMIT'){
			alert("超出可上传大小限制！");
		}
	};

	$upload.on('click', function() {
		if ($(this).hasClass('disabled')) {
			return false;
		}

		if (state === 'ready') {
			uploader.upload();
		} else if (state === 'paused') {
			uploader.upload();
		} else if (state === 'uploading') {
			uploader.stop();
		}
	});

	$info.on('click', '.retry', function() {
		uploader.retry();
	});

	$info.on('click', '.ignore', function() {
		alert('todo');
	});

	$upload.addClass('state-' + state);
	updateTotalProgress();
});