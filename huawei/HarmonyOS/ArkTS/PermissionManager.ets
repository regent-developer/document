import { abilityAccessCtrl, bundleManager, common, Permissions } from '@kit.AbilityKit';

class PermissionManager {
  // 检查是否授权
  checkPermissions(permissions: Permissions[]) {
    // ['ohos.permission.READ_CONTACTS', 'ohos.permission.WRITE_CONTACTS']
    // 程序访问控制管理
    const atManager = abilityAccessCtrl.createAtManager();
    const bundleInfo = bundleManager.getBundleInfoForSelfSync(bundleManager.BundleFlag.GET_BUNDLE_INFO_WITH_APPLICATION)
    // 提取 tokenID 标识
    const tokenID = bundleInfo.appInfo.accessTokenId;
    //检查是否已授权
    const grantStatusList = permissions.map(item => atManager.checkAccessTokenSync(tokenID, item))
    return grantStatusList.every(value => value === abilityAccessCtrl.GrantStatus.PERMISSION_GRANTED)
  }

  // 动态申请授权（首次弹窗申请）
  async requestPermissions(permissions: Permissions[]) {
    const atManager = abilityAccessCtrl.createAtManager()
    //弹起弹窗
    const permissionRequestResult = await atManager.requestPermissionsFromUser(getContext(), permissions)
    //检查权限的结果
    const res = permissionRequestResult.authResults.every(value => value === abilityAccessCtrl.GrantStatus.PERMISSION_GRANTED)
    return res === true ? Promise.resolve(true) : Promise.reject(false)
  }

  // 打开系统设置的权限管理页（处理授权结果）
  openPermissionSettingsPage() {
    const context = getContext() as common.UIAbilityContext
    // 获取包信息
    const bundleInfo = bundleManager.getBundleInfoForSelfSync(bundleManager.BundleFlag.GET_BUNDLE_INFO_WITH_APPLICATION)
    context.startAbility({
      bundleName: 'com.huawei.hmos.settings',
      abilityName: 'com.huawei.hmos.settings.MainAbility',
      uri: 'application_info_entry',
      parameters: {
        pushParams: bundleInfo.name
      }
    })
  }
}

export const permissionManager = new PermissionManager()
