package com.tobiasfertig.java.solidityartist;

public class TypeName implements Writable
{
	public static final TypeName BOOLEAN = new TypeName( "bool" );
	public static final TypeName ADDRESS = new TypeName( "address" );
	public static final TypeName ADDRESS_PAYABLE = new TypeName( "address payable" );
	public static final TypeName BYTE = new TypeName( "byte" );
	public static final TypeName BYTES = new TypeName( "bytes" );
	public static final TypeName BYTES1 = new TypeName( "bytes1" );
	public static final TypeName BYTES2 = new TypeName( "bytes2" );
	public static final TypeName BYTES3 = new TypeName( "bytes3" );
	public static final TypeName BYTES4 = new TypeName( "bytes4" );
	public static final TypeName BYTES5 = new TypeName( "bytes5" );
	public static final TypeName BYTES6 = new TypeName( "bytes6" );
	public static final TypeName BYTES7 = new TypeName( "bytes7" );
	public static final TypeName BYTES8 = new TypeName( "bytes8" );
	public static final TypeName BYTES9 = new TypeName( "bytes9" );
	public static final TypeName BYTES10 = new TypeName( "bytes10" );
	public static final TypeName BYTES11 = new TypeName( "bytes11" );
	public static final TypeName BYTES12 = new TypeName( "bytes12" );
	public static final TypeName BYTES13 = new TypeName( "bytes13" );
	public static final TypeName BYTES14 = new TypeName( "bytes14" );
	public static final TypeName BYTES15 = new TypeName( "bytes15" );
	public static final TypeName BYTES16 = new TypeName( "bytes16" );
	public static final TypeName BYTES17 = new TypeName( "bytes17" );
	public static final TypeName BYTES18 = new TypeName( "bytes18" );
	public static final TypeName BYTES19 = new TypeName( "bytes19" );
	public static final TypeName BYTES20 = new TypeName( "bytes20" );
	public static final TypeName BYTES21 = new TypeName( "bytes21" );
	public static final TypeName BYTES22 = new TypeName( "bytes22" );
	public static final TypeName BYTES23 = new TypeName( "bytes23" );
	public static final TypeName BYTES24 = new TypeName( "bytes24" );
	public static final TypeName BYTES25 = new TypeName( "bytes25" );
	public static final TypeName BYTES26 = new TypeName( "bytes26" );
	public static final TypeName BYTES27 = new TypeName( "bytes27" );
	public static final TypeName BYTES28 = new TypeName( "bytes28" );
	public static final TypeName BYTES29 = new TypeName( "bytes29" );
	public static final TypeName BYTES30 = new TypeName( "bytes30" );
	public static final TypeName BYTES31 = new TypeName( "bytes31" );
	public static final TypeName BYTES32 = new TypeName( "bytes32" );
	public static final TypeName INT = new TypeName( "int" );
	public static final TypeName INT8 = new TypeName( "int8" );
	public static final TypeName INT16 = new TypeName( "int16" );
	public static final TypeName INT24 = new TypeName( "int24" );
	public static final TypeName INT32 = new TypeName( "int32" );
	public static final TypeName INT40 = new TypeName( "int40" );
	public static final TypeName INT48 = new TypeName( "int48" );
	public static final TypeName INT56 = new TypeName( "int56" );
	public static final TypeName INT64 = new TypeName( "int64" );
	public static final TypeName INT72 = new TypeName( "int72" );
	public static final TypeName INT80 = new TypeName( "int80" );
	public static final TypeName INT88 = new TypeName( "int88" );
	public static final TypeName INT96 = new TypeName( "int96" );
	public static final TypeName INT104 = new TypeName( "int104" );
	public static final TypeName INT112 = new TypeName( "int112" );
	public static final TypeName INT120 = new TypeName( "int120" );
	public static final TypeName INT128 = new TypeName( "int128" );
	public static final TypeName INT136 = new TypeName( "int136" );
	public static final TypeName INT144 = new TypeName( "int144" );
	public static final TypeName INT152 = new TypeName( "int152" );
	public static final TypeName INT160 = new TypeName( "int160" );
	public static final TypeName INT168 = new TypeName( "int168" );
	public static final TypeName INT176 = new TypeName( "int176" );
	public static final TypeName INT184 = new TypeName( "int184" );
	public static final TypeName INT192 = new TypeName( "int192" );
	public static final TypeName INT200 = new TypeName( "int200" );
	public static final TypeName INT208 = new TypeName( "int208" );
	public static final TypeName INT216 = new TypeName( "int216" );
	public static final TypeName INT224 = new TypeName( "int224" );
	public static final TypeName INT232 = new TypeName( "int232" );
	public static final TypeName INT240 = new TypeName( "int240" );
	public static final TypeName INT248 = new TypeName( "int248" );
	public static final TypeName INT256 = new TypeName( "int256" );
	public static final TypeName STRING = new TypeName( "string" );
	public static final TypeName UINT = new TypeName( "uint" );
	public static final TypeName UINT8 = new TypeName( "uint8" );
	public static final TypeName UINT16 = new TypeName( "uint16" );
	public static final TypeName UINT24 = new TypeName( "uint24" );
	public static final TypeName UINT32 = new TypeName( "uint32" );
	public static final TypeName UINT40 = new TypeName( "uint40" );
	public static final TypeName UINT48 = new TypeName( "uint48" );
	public static final TypeName UINT56 = new TypeName( "uint56" );
	public static final TypeName UINT64 = new TypeName( "uint64" );
	public static final TypeName UINT72 = new TypeName( "uint72" );
	public static final TypeName UINT80 = new TypeName( "uint80" );
	public static final TypeName UINT88 = new TypeName( "uint88" );
	public static final TypeName UINT96 = new TypeName( "uint96" );
	public static final TypeName UINT104 = new TypeName( "uint104" );
	public static final TypeName UINT112 = new TypeName( "uint112" );
	public static final TypeName UINT120 = new TypeName( "uint120" );
	public static final TypeName UINT128 = new TypeName( "uint128" );
	public static final TypeName UINT136 = new TypeName( "uint136" );
	public static final TypeName UINT144 = new TypeName( "uint144" );
	public static final TypeName UINT152 = new TypeName( "uint152" );
	public static final TypeName UINT160 = new TypeName( "uint160" );
	public static final TypeName UINT168 = new TypeName( "uint168" );
	public static final TypeName UINT176 = new TypeName( "uint176" );
	public static final TypeName UINT184 = new TypeName( "uint184" );
	public static final TypeName UINT192 = new TypeName( "uint192" );
	public static final TypeName UINT200 = new TypeName( "uint200" );
	public static final TypeName UINT208 = new TypeName( "uint208" );
	public static final TypeName UINT216 = new TypeName( "uint216" );
	public static final TypeName UINT224 = new TypeName( "uint224" );
	public static final TypeName UINT232 = new TypeName( "uint232" );
	public static final TypeName UINT240 = new TypeName( "uint240" );
	public static final TypeName UINT248 = new TypeName( "uint248" );
	public static final TypeName UINT256 = new TypeName( "uint256" );
	public final static TypeName WILDCARD = new TypeName( "*" );

	private final String keyword;

	public TypeName( String keyword )
	{
		this.keyword = keyword;
	}

	public TypeName( TypeName typeName )
	{
		this.keyword = typeName.keyword;
	}

	@Override public void write( CodeWriter writer )
	{
		writer.write( keyword );
	}
}
