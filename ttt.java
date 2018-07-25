pubilc class CommonController extends BaseController {  
    Log log = LogFactory.getLog(CommonController.class);  
      
    Properties fileUploadPro = null;  
    public CommonController(){  
        fileUploadPro = PropertiesUtil.getPropertiesByClass("fileupload.properties");  
    }  
      
      
    @Override  
    public ModeAndView init(HttpServletRequest request,  
            HttpServletResponse response) throws ServletException, IOException {  
          
        return null;  
    }  
      
    /** 
     * 璺宠浆鍒版枃浠朵笂浼犻〉 
     * @param request 
     * @param response 
     * @return 
     * @throws ServletException 
     * @throws IOException 
     */  
    public ModeAndView goFileUpload(HttpServletRequest request,  
            HttpServletResponse response) throws ServletException, IOException {  
        String functionId = request.getParameter("functionId");  
        String fileType = request.getParameter("fileType");  
        String maxSize = request.getParameter("maxSize");  
        ModeAndView mav = new ModeAndView("/WEB-INF/jsp/common/fileUpload.jsp");  
          
        if(functionId!=null && !"".equals(functionId.trim())){  
            mav.addObject("functionId", functionId);  
        }  
        if(fileType!=null && !"".equals(fileType.trim())){  
            mav.addObject("fileType", fileType);  
        }  
        if(maxSize!=null && !"".equals(maxSize.trim())){  
            mav.addObject("maxSize", maxSize);  
        }  
        return mav;  
    }  
      
    /** 
     * 涓婁紶鏂囦欢 
     * @param request 
     * @param response 
     * @return 
     * @throws ServletException 
     * @throws IOException 
     */  
    @SuppressWarnings("unchecked")  
    public ModeAndView doFileUpload(HttpServletRequest request,  
            HttpServletResponse response) throws ServletException, IOException {  
        //鑾峰彇骞惰В鏋愭枃浠剁被鍨嬪拰鏀寔鏈€澶у€�  
        String functionId = request.getParameter("functionId");  
        String fileType = request.getParameter("fileType");  
        String maxSize = request.getParameter("maxSize");  
          
        //涓存椂鐩綍鍚�  
        String tempPath = fileUploadPro.getProperty("tempPath");  
        //鐪熷疄鐩綍鍚�  
        String filePath = fileUploadPro.getProperty("filePath");  
          
        FileUtil.createFolder(tempPath);  
        FileUtil.createFolder(filePath);  
          
        DiskFileItemFactory factory = new DiskFileItemFactory();  
        //鏈€澶х紦瀛�  
        factory.setSizeThreshold(5*1024);  
        //璁剧疆涓存椂鏂囦欢鐩綍  
        factory.setRepository(new File(tempPath));  
        ServletFileUpload upload = new ServletFileUpload(factory);  
        if(maxSize!=null && !"".equals(maxSize.trim())){  
            //鏂囦欢鏈€澶т笂闄�  
            upload.setSizeMax(Integer.valueOf(maxSize)*1024*1024);  
        }  
          
        try {  
            //鑾峰彇鎵€鏈夋枃浠跺垪琛�  
            List<FileItem> items = upload.parseRequest(request);  
            for (FileItem item : items) {  
                if(!item.isFormField()){  
                    //鏂囦欢鍚�  
                    String fileName = item.getName();  
                      
                    //妫€鏌ユ枃浠跺悗缂€鏍煎紡  
                    String fileEnd = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();  
                    if(fileType!=null && !"".equals(fileType.trim())){  
                        boolean isRealType = false;  
                        String[] arrType = fileType.split(",");  
                        for (String str : arrType) {  
                            if(fileEnd.equals(str.toLowerCase())){  
                                isRealType = true;  
                                break;  
                            }  
                        }  
                        if(!isRealType){  
                            //鎻愮ず閿欒淇℃伅:鏂囦欢鏍煎紡涓嶆纭�  
                            super.printJsMsgBack(response, "鏂囦欢鏍煎紡涓嶆纭�!");  
                            return null;  
                        }  
                    }  
                      
                    //鍒涘缓鏂囦欢鍞竴鍚嶇О  
                    String uuid = UUID.randomUUID().toString();  
                    //鐪熷疄涓婁紶璺緞  
                    StringBuffer sbRealPath = new StringBuffer();  
                    sbRealPath.append(filePath).append(uuid).append(".").append(fileEnd);  
                    //鍐欏叆鏂囦欢  
                    File file = new File(sbRealPath.toString());  
                    item.write(file);  
                    //涓婁紶鎴愬姛锛屽悜鐖剁獥浣撹繑鍥炴暟鎹�:鐪熷疄鏂囦欢鍚�,铏氭嫙鏂囦欢鍚�,鏂囦欢澶у皬  
                    StringBuffer sb = new StringBuffer();  
                    sb.append("window.returnValue='").append(fileName).append(",").append(uuid).append(".").append(fileEnd).append(",").append(file.length()).append("';");  
                    sb.append("window.close();");  
                    super.printJsMsg(response, sb.toString());  
                    log.info("涓婁紶鏂囦欢鎴愬姛,JS淇℃伅锛�"+sb.toString());  
                }//end of if  
            }//end of for  
              
        }catch (Exception e) {  
            //鎻愮ず閿欒:姣斿鏂囦欢澶у皬  
            super.printJsMsgBack(response, "涓婁紶澶辫触,鏂囦欢澶у皬涓嶈兘瓒呰繃"+maxSize+"M!");  
            log.error("涓婁紶鏂囦欢寮傚父!",e);  
            return null;  
        }  
          
        return null;  
    }  
} 
