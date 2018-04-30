using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEditor;
using UnityEditor.Build;
using UnityEditor.iOS.Xcode;
using UnityEngine;

namespace SiegeModule {
    public class UniKeyModuleBuildProcessor : IPostprocessBuild {
        /// <summary>
        /// Order
        /// </summary>
        int IOrderedCallback.callbackOrder { get { return 0; } }

        /// <summary>
        /// PostProcess
        /// </summary>
        /// <param name="target"></param>
        /// <param name="path"></param>
        void IPostprocessBuild.OnPostprocessBuild(BuildTarget target, string path) {
            switch (target)
            {
                case BuildTarget.iOS:
                    var projectPath = PBXProject.GetPBXProjectPath(path);
                    var project = new PBXProject();
                    project.ReadFromString(File.ReadAllText(projectPath));
                    var targetGUID = project.TargetGuidByName("Unity-iPhone");
                    project.SetBuildProperty(targetGUID, "GCC_ENABLE_OBJC_EXCEPTIONS", "YES");
                    File.WriteAllText(projectPath, project.WriteToString());
                    break;
            }
        }
    }
}