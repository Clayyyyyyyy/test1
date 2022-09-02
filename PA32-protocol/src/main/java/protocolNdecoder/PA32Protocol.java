package protocolNdecoder;

import java.util.HashMap;
import java.util.Map;

import com.yisuitech.iot.base.protocol.IProtocol;
import com.yisuitech.iot.base.protocol.IProtocolDecoder;
import com.yisuitech.iot.base.protocol.SocketDecoderType;
import com.yisuitech.iot.entity.protocol.CmdDataType;
import com.yisuitech.iot.entity.protocol.fun.FunCodeItem;
import com.yisuitech.iot.entity.protocol.fun.FunCodeType;
import com.yisuitech.iot.entity.protocol.para.ProtocolPara;
import com.yisuitech.iot.protocol.base.AbstractProtocol;
import com.yisuitech.iot.protocol.util.ProtocolBuilder;

public class PA32Protocol extends AbstractProtocol implements IProtocol {
		private PA32Decoder decoder;
		private final String protocolName = "Wialon-Combine";
		
		@Override
		public String getProtocolName() {
			return protocolName;
		}
		
		@Override
		public CmdDataType getCmdDataType() {
			return CmdDataType.ByteArray;
		}
		
		@Override
		public boolean needContext() {
			return true;
		}
		
		@Override
		public Map<String, FunCodeItem> getFunCodes() {
			return ProtocolBuilder.builder()
				 	   // FunCodeItem(funCode, needResp, needSave, FunCOdeType)
				 .add(new FunCodeItem("0", true, true, FunCodeType.Other), false)		// Login
				 
				 .add(new FunCodeItem("1", false, true, FunCodeType.Position), false)	// Data Package
				 
				 .add(new FunCodeItem("2", false, false, FunCodeType.Other), false)		// Keep-Alive
				 
				 .add(new FunCodeItem("3", false, true, FunCodeType.Response), false)	// ACK
				 .build();
		}
		
		@Override
		public IProtocolDecoder getDecoder() {
			if(decoder == null)	decoder = new PA32Decoder(this);
			return decoder;
		}
		
		@Override
		protected void  initPara() {
			if(paras == null) {
				synchronized (PA32Protocol.class) {
					if(paras == null) {
						paras = new HashMap<String, ProtocolPara>();
					}
				}
			}
		}
		
		@Override
		public int getInterval() {
			return 60;
		}
		
		@Override
		public SocketDecoderType getSocketDecoderType() {
			return SocketDecoderType.DefaultDecoder;
		}
}
