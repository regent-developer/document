# Document Picture-in-Picture API

这是浏览器提供的原生 API，它允许你创建一个独立的、始终置顶的小窗口，并加载自定义 HTML 内容

async function openPipWindow() {
  if (!("documentPictureInPicture" in window)) return;

  const pipWindow = await documentPictureInPicture.requestWindow({
    width: 400,
    height: 300
  });

  // 设置窗口内容（你可以用框架进一步封装）
  pipWindow.document.body.innerHTML = \`
    <div style="padding: 20px; background: #f0f0f0;">
      <h2>自定义浮窗</h2>
      <p>这是对 window.open 的完美替代</p>
    </div>
  \`;
}