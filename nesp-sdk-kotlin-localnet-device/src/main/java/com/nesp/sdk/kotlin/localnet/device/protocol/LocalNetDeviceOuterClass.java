// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: LocalNetDevice.proto

package com.nesp.sdk.kotlin.localnet.device.protocol;

public final class LocalNetDeviceOuterClass {
  private LocalNetDeviceOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_nesp_sdk_kotlin_localnet_device_protocol_LocalNetDevice_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_nesp_sdk_kotlin_localnet_device_protocol_LocalNetDevice_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\024LocalNetDevice.proto\022,com.nesp.sdk.kot" +
      "lin.localnet.device.protocol\"\367\006\n\016LocalNe" +
      "tDevice\022\020\n\003did\030\001 \001(\tH\000\210\001\001\022\020\n\003uid\030\002 \001(\tH\001" +
      "\210\001\001\022\017\n\002ip\030\003 \001(\tH\002\210\001\001\022\025\n\010udp_port\030\004 \001(\005H\003" +
      "\210\001\001\022\021\n\004flag\030\005 \001(\005H\004\210\001\001\022\031\n\014linked_count\030\006" +
      " \001(\005H\005\210\001\001\022\021\n\004name\030\007 \001(\tH\006\210\001\001\022\022\n\005model\030\010 " +
      "\001(\tH\007\210\001\001\022\022\n\005brand\030\t \001(\tH\010\210\001\001\022\022\n\005board\030\n " +
      "\001(\tH\t\210\001\001\022a\n\013system_type\030\013 \001(\0162G.com.nesp" +
      ".sdk.kotlin.localnet.device.protocol.Loc" +
      "alNetDevice.SystemTypeH\n\210\001\001\022\030\n\013system_na" +
      "me\030\014 \001(\tH\013\210\001\001\022 \n\023system_version_code\030\r \001" +
      "(\005H\014\210\001\001\022 \n\023system_version_name\030\016 \001(\tH\r\210\001" +
      "\001\022\030\n\013system_arch\030\017 \001(\tH\016\210\001\001\022\025\n\010app_name\030" +
      "\020 \001(\tH\017\210\001\001\022\035\n\020app_version_code\030\021 \001(\005H\020\210\001" +
      "\001\022\035\n\020app_version_name\030\022 \001(\tH\021\210\001\001\"p\n\nSyst" +
      "emType\022\013\n\007UNKNOWN\020\000\022\013\n\007ANDROID\020\001\022\007\n\003IOS\020" +
      "\002\022\t\n\005LINUX\020\003\022\013\n\007WINDOWS\020\004\022\n\n\006MAC_OS\020\005\022\016\n" +
      "\nHARMONY_OS\020\006\022\013\n\007FUCHSIA\020\007B\006\n\004_didB\006\n\004_u" +
      "idB\005\n\003_ipB\013\n\t_udp_portB\007\n\005_flagB\017\n\r_link" +
      "ed_countB\007\n\005_nameB\010\n\006_modelB\010\n\006_brandB\010\n" +
      "\006_boardB\016\n\014_system_typeB\016\n\014_system_nameB" +
      "\026\n\024_system_version_codeB\026\n\024_system_versi" +
      "on_nameB\016\n\014_system_archB\013\n\t_app_nameB\023\n\021" +
      "_app_version_codeB\023\n\021_app_version_nameB\002" +
      "P\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_nesp_sdk_kotlin_localnet_device_protocol_LocalNetDevice_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_nesp_sdk_kotlin_localnet_device_protocol_LocalNetDevice_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_nesp_sdk_kotlin_localnet_device_protocol_LocalNetDevice_descriptor,
        new java.lang.String[] { "Did", "Uid", "Ip", "UdpPort", "Flag", "LinkedCount", "Name", "Model", "Brand", "Board", "SystemType", "SystemName", "SystemVersionCode", "SystemVersionName", "SystemArch", "AppName", "AppVersionCode", "AppVersionName", "Did", "Uid", "Ip", "UdpPort", "Flag", "LinkedCount", "Name", "Model", "Brand", "Board", "SystemType", "SystemName", "SystemVersionCode", "SystemVersionName", "SystemArch", "AppName", "AppVersionCode", "AppVersionName", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
