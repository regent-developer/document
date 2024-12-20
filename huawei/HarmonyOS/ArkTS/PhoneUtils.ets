import bundleManager from '@ohos.bundle.bundleManager';
import { deviceInfo } from '@kit.BasicServicesKit';
import { intl } from '@kit.LocalizationKit';

/**
 * 获取手机信息
 */
export default class PhoneUtils {
  private static instance: PhoneUtils = new PhoneUtils();
  private versionCode: string = ""
  private versionName: string = ""

  private constructor() {
    let bundleFlags = bundleManager.BundleFlag.GET_BUNDLE_INFO_DEFAULT
    const data:bundleManager.BundleInfo = bundleManager.getBundleInfoForSelfSync(bundleFlags)
    this.versionCode = data.versionCode.toString()
    this.versionName = data.versionName.toString()
  }


  public static getInstance(): PhoneUtils {
    return PhoneUtils.instance
  }

  /**
   * 獲取 Code
   * @returns
   */
  getAppCode(): string {
    return this.versionCode
  }



  /**
   * 获取 Version
   * @returns
   */
  getVersionName(): string {
    return this.versionName
  }

  /**
   * 手機版本
   */
  getPhoneVersion(): string {
    return deviceInfo.osFullName
  }


  /**
   * 获取系统语言
   */
  getPhoneLanguage(): string {
    let locale = new intl.Locale();
    return locale.language
  }
}
