package protocolNdecoder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import com.google.common.collect.Lists;
import com.yisuitech.iot.base.protocol.IProtocol;
import com.yisuitech.iot.base.protocol.IProtocolContext;
import com.yisuitech.iot.base.protocol.IProtocolDecoder;
import com.yisuitech.iot.entity.impl.GpsData;
import com.yisuitech.iot.entity.protocol.fun.FunCodeItem;
import com.yisuitech.iot.entity.protocol.para.ProtocolPara;
import com.yisuitech.iot.entity.protocol.CmdData;
import com.yisuitech.iot.protocol.ProtocolUtil;
import com.yisuitech.iot.protocol.base.SunTechParse;
import com.yisuitech.iot.protocol.util.ZlibUtil;
import com.yisuitech.iot.protocol.entity.GpsDataStatusType;
import com.yisuitech.util.HexUtil;
import com.yisuitech.util.LogUtil;
import com.yisuitech.util.StringUtil;
import com.yisuitech.util.log.CoreLogger;

public class PA32Decoder implements IProtocolDecoder {
	private CoreLogger logger = LogUtil.getLogger(PA32Decoder.class.getName());
	private IProtocol protocol;
	private String protoclName;
	private SunTechParse parse;
	
	/* Constructor */
	public PA32Decoder(IProtocol protocol) {
		this.protocol = protocol;
		this.protoclName = protocol.getProtocolName();
		this.parse = new SunTechParse(protocol.getTimeZone());
	}
	
	/* Assemble the command */
	@Override
	public byte[] assembleCmd(String funCode, ProtocolPara para) {
		byte[] res = null;
		switch(funCode) {
		case "GTQSS":
			res = assembleGTQSS(funCode, para);
			break;
		default:
			logger.warn(String.format("%s Decoder assembleCmd: not support fun(%s)", funCode));
			break;
		}
		return res;
	}
	
	/* Decode */
	@Override
	public Object decode(CmdData cmd, boolean needHanlde) {
		FunCodeItem fci = protocol.getFunCodes().get(cmd.getFunCode());
		
		Object res = null;
		String raw = new String(cmd.getData());
		String[] data = raw.split(";");
		
		switch(cmd.getFunCode()) {
			// TODO
		}
	}
	
	private Object handleAck(String src) {
		HashMap<String, String> map = new HashMap<>();
		
		map.put("Seq#", src.substring(6, 10));
		int len = src.length();
		int lenUsf = HexUtil.hex2Int(src.substring(10, 14));
		
		for(int i = 14; i < 14 + lenUsf; i += 2) {
			map.put("value", String.valueOf(HexUtil.hex2Int(src.substring(i, i+2))));
		}
		
		map.put("CRC", src.substring(14+lenUsf, len));
	}
	
	private byte[] handleACK(CmdData cmd) {
		`
	}
}
