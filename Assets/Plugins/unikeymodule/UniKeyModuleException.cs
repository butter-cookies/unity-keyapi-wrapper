using System;

namespace SiegeModule {
    /// <summary>
    /// UniKeyModule Original Exception
    /// </summary>
    public class UniKeyModuleException : Exception {
        /// <summary>
        /// error code
        /// </summary>
        public ErrorCode ErrorCode { get; private set; }

        /// <summary>
        /// constructor
        /// </summary>
        /// <param name="errorCode">errorCode</param>
        public UniKeyModuleException(ErrorCode errorCode = ErrorCode.None) {
            ErrorCode = errorCode;
        }

        /// <summary>
        /// constructor
        /// </summary>
        /// <param name="msg">errorMessage</param>
        /// <param name="errorCode">errorCode</param>
        public UniKeyModuleException(string msg, ErrorCode errorCode = ErrorCode.None) : base(msg) {
            ErrorCode = errorCode;
        }

        /// <summary>
        /// constructor
        /// </summary>
        /// <param name="msg">errorMessage</param>
        /// <param name="inner">exception</param>
        /// <param name="errorCode">errorCode</param>
        public UniKeyModuleException(string msg, Exception inner, ErrorCode errorCode = ErrorCode.None) : base(msg, inner) {
            ErrorCode = errorCode;
        }
    }
}
