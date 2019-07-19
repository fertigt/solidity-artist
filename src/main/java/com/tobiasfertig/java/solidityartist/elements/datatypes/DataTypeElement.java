package com.tobiasfertig.java.solidityartist.elements.datatypes;

import com.tobiasfertig.java.solidityartist.elements.SolidityElement;
import com.tobiasfertig.java.solidityartist.visitors.Visitor;

import java.sql.SQLOutput;

public class DataTypeElement implements SolidityElement
{
	public static final DataTypeElement BOOL = new DataTypeElement( "bool" );
	public static final DataTypeElement ADDRESS = new DataTypeElement( "address" );
	public static final DataTypeElement ADDRESS_PAYABLE = new DataTypeElement( "address payable" );
	public static final DataTypeElement BYTE = new DataTypeElement( "byte" );
	public static final DataTypeElement BYTES = new DataTypeElement( "bytes" );
	public static final DataTypeElement BYTES1 = new DataTypeElement( "bytes1" );
	public static final DataTypeElement BYTES2 = new DataTypeElement( "bytes2" );
	public static final DataTypeElement BYTES3 = new DataTypeElement( "bytes3" );
	public static final DataTypeElement BYTES4 = new DataTypeElement( "bytes4" );
	public static final DataTypeElement BYTES5 = new DataTypeElement( "bytes5" );
	public static final DataTypeElement BYTES6 = new DataTypeElement( "bytes6" );
	public static final DataTypeElement BYTES7 = new DataTypeElement( "bytes7" );
	public static final DataTypeElement BYTES8 = new DataTypeElement( "bytes8" );
	public static final DataTypeElement BYTES9 = new DataTypeElement( "bytes9" );
	public static final DataTypeElement BYTES10 = new DataTypeElement( "bytes10" );
	public static final DataTypeElement BYTES11 = new DataTypeElement( "bytes11" );
	public static final DataTypeElement BYTES12 = new DataTypeElement( "bytes12" );
	public static final DataTypeElement BYTES13 = new DataTypeElement( "bytes13" );
	public static final DataTypeElement BYTES14 = new DataTypeElement( "bytes14" );
	public static final DataTypeElement BYTES15 = new DataTypeElement( "bytes15" );
	public static final DataTypeElement BYTES16 = new DataTypeElement( "bytes16" );
	public static final DataTypeElement BYTES17 = new DataTypeElement( "bytes17" );
	public static final DataTypeElement BYTES18 = new DataTypeElement( "bytes18" );
	public static final DataTypeElement BYTES19 = new DataTypeElement( "bytes19" );
	public static final DataTypeElement BYTES20 = new DataTypeElement( "bytes20" );
	public static final DataTypeElement BYTES21 = new DataTypeElement( "bytes21" );
	public static final DataTypeElement BYTES22 = new DataTypeElement( "bytes22" );
	public static final DataTypeElement BYTES23 = new DataTypeElement( "bytes23" );
	public static final DataTypeElement BYTES24 = new DataTypeElement( "bytes24" );
	public static final DataTypeElement BYTES25 = new DataTypeElement( "bytes25" );
	public static final DataTypeElement BYTES26 = new DataTypeElement( "bytes26" );
	public static final DataTypeElement BYTES27 = new DataTypeElement( "bytes27" );
	public static final DataTypeElement BYTES28 = new DataTypeElement( "bytes28" );
	public static final DataTypeElement BYTES29 = new DataTypeElement( "bytes29" );
	public static final DataTypeElement BYTES30 = new DataTypeElement( "bytes30" );
	public static final DataTypeElement BYTES31 = new DataTypeElement( "bytes31" );
	public static final DataTypeElement BYTES32 = new DataTypeElement( "bytes32" );
	public static final DataTypeElement FIXED = new DataTypeElement( "fixed" );
	public static final DataTypeElement INT = new DataTypeElement( "int" );
	public static final DataTypeElement INT8 = new DataTypeElement( "int8" );
	public static final DataTypeElement INT16 = new DataTypeElement( "int16" );
	public static final DataTypeElement INT24 = new DataTypeElement( "int24" );
	public static final DataTypeElement INT32 = new DataTypeElement( "int32" );
	public static final DataTypeElement INT40 = new DataTypeElement( "int40" );
	public static final DataTypeElement INT48 = new DataTypeElement( "int48" );
	public static final DataTypeElement INT56 = new DataTypeElement( "int56" );
	public static final DataTypeElement INT64 = new DataTypeElement( "int64" );
	public static final DataTypeElement INT72 = new DataTypeElement( "int72" );
	public static final DataTypeElement INT80 = new DataTypeElement( "int80" );
	public static final DataTypeElement INT88 = new DataTypeElement( "int88" );
	public static final DataTypeElement INT96 = new DataTypeElement( "int96" );
	public static final DataTypeElement INT104 = new DataTypeElement( "int104" );
	public static final DataTypeElement INT112 = new DataTypeElement( "int112" );
	public static final DataTypeElement INT120 = new DataTypeElement( "int120" );
	public static final DataTypeElement INT128 = new DataTypeElement( "int128" );
	public static final DataTypeElement INT136 = new DataTypeElement( "int136" );
	public static final DataTypeElement INT144 = new DataTypeElement( "int144" );
	public static final DataTypeElement INT152 = new DataTypeElement( "int152" );
	public static final DataTypeElement INT160 = new DataTypeElement( "int160" );
	public static final DataTypeElement INT168 = new DataTypeElement( "int168" );
	public static final DataTypeElement INT176 = new DataTypeElement( "int176" );
	public static final DataTypeElement INT184 = new DataTypeElement( "int184" );
	public static final DataTypeElement INT192 = new DataTypeElement( "int192" );
	public static final DataTypeElement INT200 = new DataTypeElement( "int200" );
	public static final DataTypeElement INT208 = new DataTypeElement( "int208" );
	public static final DataTypeElement INT216 = new DataTypeElement( "int216" );
	public static final DataTypeElement INT224 = new DataTypeElement( "int224" );
	public static final DataTypeElement INT232 = new DataTypeElement( "int232" );
	public static final DataTypeElement INT240 = new DataTypeElement( "int240" );
	public static final DataTypeElement INT248 = new DataTypeElement( "int248" );
	public static final DataTypeElement INT256 = new DataTypeElement( "int256" );
	public static final DataTypeElement STRING = new DataTypeElement( "string" );
	public static final DataTypeElement UFIXED = new DataTypeElement( "ufixed" );
	public static final DataTypeElement UINT = new DataTypeElement( "uint" );
	public static final DataTypeElement UINT8 = new DataTypeElement( "uint8" );
	public static final DataTypeElement UINT16 = new DataTypeElement( "uint16" );
	public static final DataTypeElement UINT24 = new DataTypeElement( "uint24" );
	public static final DataTypeElement UINT32 = new DataTypeElement( "uint32" );
	public static final DataTypeElement UINT40 = new DataTypeElement( "uint40" );
	public static final DataTypeElement UINT48 = new DataTypeElement( "uint48" );
	public static final DataTypeElement UINT56 = new DataTypeElement( "uint56" );
	public static final DataTypeElement UINT64 = new DataTypeElement( "uint64" );
	public static final DataTypeElement UINT72 = new DataTypeElement( "uint72" );
	public static final DataTypeElement UINT80 = new DataTypeElement( "uint80" );
	public static final DataTypeElement UINT88 = new DataTypeElement( "uint88" );
	public static final DataTypeElement UINT96 = new DataTypeElement( "uint96" );
	public static final DataTypeElement UINT104 = new DataTypeElement( "uint104" );
	public static final DataTypeElement UINT112 = new DataTypeElement( "uint112" );
	public static final DataTypeElement UINT120 = new DataTypeElement( "uint120" );
	public static final DataTypeElement UINT128 = new DataTypeElement( "uint128" );
	public static final DataTypeElement UINT136 = new DataTypeElement( "uint136" );
	public static final DataTypeElement UINT144 = new DataTypeElement( "uint144" );
	public static final DataTypeElement UINT152 = new DataTypeElement( "uint152" );
	public static final DataTypeElement UINT160 = new DataTypeElement( "uint160" );
	public static final DataTypeElement UINT168 = new DataTypeElement( "uint168" );
	public static final DataTypeElement UINT176 = new DataTypeElement( "uint176" );
	public static final DataTypeElement UINT184 = new DataTypeElement( "uint184" );
	public static final DataTypeElement UINT192 = new DataTypeElement( "uint192" );
	public static final DataTypeElement UINT200 = new DataTypeElement( "uint200" );
	public static final DataTypeElement UINT208 = new DataTypeElement( "uint208" );
	public static final DataTypeElement UINT216 = new DataTypeElement( "uint216" );
	public static final DataTypeElement UINT224 = new DataTypeElement( "uint224" );
	public static final DataTypeElement UINT232 = new DataTypeElement( "uint232" );
	public static final DataTypeElement UINT240 = new DataTypeElement( "uint240" );
	public static final DataTypeElement UINT248 = new DataTypeElement( "uint248" );
	public static final DataTypeElement UINT256 = new DataTypeElement( "uint256" );
	public static final DataTypeElement WILDCARD = new DataTypeElement( "*" );
	
	private final String typeName;

	public DataTypeElement( String typeName )
	{
		this.typeName = typeName;
	}

	@Override public void accept( Visitor visitor )
	{
		visitor.visit( this );
	}

	public String getTypeName( )
	{
		return typeName;
	}

	@Override public String toString( )
	{
		return typeName;
	}
}
